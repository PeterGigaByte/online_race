package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Results {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;

    public Results(RaceService raceService,DisciplineService disciplineService, ApResultsService apResultsService,ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "results";
    }
    @ModelAttribute("span")
    public String span(){
        if(activeRace()!=null && activeRace().getSettings().getReactions()==1){
            return "5";
        }else{
            return "4";
        }
    }
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }
    @GetMapping("/results")
    public String disciplines(Model model){
        List<Discipline> disciplineList = disciplineService.findDisciplinesByRaceId(activeRace().getId());
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplineList) {
            map.put(discipline,apResultsService.findAllByDisciplineRaceIdAndDisciplineId(activeRace().getId(),discipline.getId()));
        }
        model.addAttribute("disciplines",disciplineList);
        model.addAttribute("startListMap",map);
        model.addAttribute("bibMap",apResultsService.findByRaceIdMap(activeRace().getId()));
        model.addAttribute("clubs",clubParticipantsService.findRealClubs(activeRace().getStartDate(),activeRace().getStartDate(),activeRace().getStartDate()));
        return "results/results";
    }
    @GetMapping("/results/setDns/{id}")
    public String setDns(@PathVariable Integer id){
        ResultStartList resultStartList = apResultsService.findById(id);
        if(resultStartList!=null && resultStartList.getDiscipline().getDisciplineType().equals("run")){
            if( resultStartList.getStatus()!= null && resultStartList.getStatus().equals("DNS")){
                resultStartList.setStatus(null);
            }else{
                apResultsService.clearResults(resultStartList);
                resultStartList.setReaction(null);
                resultStartList.setStatus("DNS");
            }

            apResultsService.saveResultStartList(resultStartList);
        }
        return "redirect:/results";
    }
    @GetMapping("/results/setDnf/{id}")
    public String setDnf(@PathVariable Integer id){
        ResultStartList resultStartList = apResultsService.findById(id);

        if(resultStartList!=null && resultStartList.getDiscipline().getDisciplineType().equals("run")){
            if(resultStartList.getStatus()!= null && resultStartList.getStatus().equals("DNF")){
                resultStartList.setStatus(null);
            }else{
                apResultsService.clearResults(resultStartList);
                resultStartList.setStatus("DNF");
            }
            apResultsService.saveResultStartList(resultStartList);
        }
        return "redirect:/results";
    }
    @GetMapping("/results/setDq/{id}")
    public String setDq(@PathVariable Integer id){
        ResultStartList resultStartList = apResultsService.findById(id);
        if(resultStartList!=null && resultStartList.getDiscipline().getDisciplineType().equals("run")){
            if(resultStartList.getStatus()!= null && resultStartList.getStatus().equals("DQ")){
                resultStartList.setStatus(null);
            }else{
                apResultsService.clearResults(resultStartList);
                resultStartList.setStatus("DQ");
            }
            apResultsService.saveResultStartList(resultStartList);
        }
        return "redirect:/results";
    }
    @PostMapping(value = "/results/edit/results")
    public String editResults(@RequestBody JsonNode jsonNode){
        for (int i = 0; i<jsonNode.size()-1; i++){
            Double resultPerformance = jsonNode.get(i).get("Výkon atléta").asDouble();
            int resultStartListId = jsonNode.get(i).get("Meno").asInt();
            ResultStartList resultStartList = apResultsService.findById(resultStartListId);

            if(!resultStartList.getDiscipline().getDisciplineType().equals("run")){
                resultStartList.setResultPerformance(resultPerformance);
            }
            else if(!jsonNode.get(i).get("Výkon atléta").asText().equals(resultStartList.getTimeResultPerformance())){
                if(resultPerformance==0.0){
                    resultStartList.setResultPerformance(null);
                }
                resultStartList.setResultPerformance(resultPerformance);
            }
            if(activeRace().getSettings().getReactions()==1 && resultStartList.getDiscipline().getDisciplineType().equals("run")){
                String reaction = jsonNode.get(i).get("Reakčný čas").asText();
                if(!reaction.isEmpty() && !reaction.equals(resultStartList.getReactions())){
                    Double reactionDouble = Double.valueOf(reaction);
                    if (reactionDouble <= 9999 && reactionDouble >= -9999){
                        reactionDouble=reactionDouble/1000;
                        resultStartList.setReaction(reactionDouble);
                    }else{
                        resultStartList.setReaction(null);
                    }
                }
            }
            apResultsService.saveResultStartList(resultStartList);
        }
        int disciplineId = jsonNode.get(jsonNode.size()-1).get("id").asInt();
        List <ResultStartList> resultStartLists;
        if(!disciplineService.findDisciplineById(disciplineId).getDisciplineType().equals("run")){
            resultStartLists = apResultsService.findResultStartListByDisciplineIdOrderByResultPerformanceDesc(disciplineId);
        }else{
            resultStartLists = apResultsService.findAllByDisciplineIdOrderByResultPerformanceAsc(disciplineId);
        }
        int order = 1;
        ResultStartList previous = null;
        for (ResultStartList resultStartList: resultStartLists) {
            if(previous != null && previous.getResultPerformance()!=null && resultStartList.getResultPerformance()!=null && previous.getResultPerformance().equals(resultStartList.getResultPerformance()) && resultStartList.getResultPerformance()!=null && resultStartList.getResultPerformance()!=0.0){
                order--;
                resultStartList.setPlace(+order +"=.");
                order++;
            }
            else if(resultStartList.getResultPerformance()!=null && resultStartList.getResultPerformance()!=0.0){
                resultStartList.setPlace(order+".");
                order++;
            }else{
                resultStartList.setPlace(null);
            }

            previous = resultStartList;
            apResultsService.saveResultStartList(resultStartList);
        }
        return "redirect:/results";
    }
}

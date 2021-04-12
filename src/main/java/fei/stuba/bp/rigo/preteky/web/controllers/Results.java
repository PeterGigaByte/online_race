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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            apResultsService.saveResultStartList(resultStartList);
        }
        int disciplineId = jsonNode.get(jsonNode.size()-1).get("id").asInt();
        List <ResultStartList> resultStartLists = apResultsService.findAllByDisciplineIdOrderByResultPerformanceAsc(disciplineId);
        int order = 1;
        for (ResultStartList resultStartList: resultStartLists) {
            if(resultStartList.getResultPerformance()!=null && resultStartList.getResultPerformance()!=0.0){
                resultStartList.setPlace(order+".");
            }else{
                resultStartList.setPlace(null);
                order--;
            }

            order++;
            apResultsService.saveResultStartList(resultStartList);
        }
        return "redirect:/results";
    }
}

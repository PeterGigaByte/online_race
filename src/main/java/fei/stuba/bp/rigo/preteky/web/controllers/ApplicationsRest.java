package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.csvFilesImplementation.ExportStartList;
import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/applications")
public class ApplicationsRest {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;
    private ExportStartList exportStartList = new ExportStartList();
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }
    public ApplicationsRest(RaceService raceService,DisciplineService disciplineService, ApResultsService apResultsService,ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
        exportStartList.createDisciplinesLength();
    }
    @PostMapping(value = "/athletes")
    public List<ClubTransfer> getAthletes(@RequestBody JsonNode jsonNode) {
        int clubId = jsonNode.get("idClub").asInt();
        String gender = jsonNode.get("gender").asText();
        Date date = activeRace().getStartDate();
        List<ClubTransfer> clubTransfers = clubParticipantsService.findRealAthletesOfClub(date, date,clubId,gender, date,clubId,gender);
        System.out.println(clubTransfers);
        return clubTransfers ;
    }
    @PostMapping(value = "/save")
    public String saveAthletes(@RequestBody JsonNode jsonNode) {
        int disciplineId = jsonNode.get(jsonNode.size()-1).get("id").asInt();
        System.out.println("disciplineId: "+disciplineId);
        for (int i = 0; i<jsonNode.size()-1; i++){
            int line = jsonNode.get(i).get("Dráha").asInt();
            int bib = jsonNode.get(i).get("Číslo").asInt();
            int idAthlete = jsonNode.get(i).get("Zmazať").asInt();
            if(apResultsService.findByAthleteIdAndDisciplineId(idAthlete,disciplineId).isEmpty()){
                ResultStartList resultStartList = new ResultStartList();
                resultStartList.setAthlete(clubParticipantsService.findAthlete(idAthlete));
                resultStartList.setDiscipline(disciplineService.findDisciplineById(disciplineId));
                resultStartList.setLine(line);
                apResultsService.saveResultStartList(resultStartList);
                Bib bibR = apResultsService.findByRaceIdAndAthleteId(activeRace().getId(),idAthlete);
                if (bibR!=null){
                    bibR.setBib(bib);
                }else{
                    bibR = new Bib();
                    bibR.setBib(bib);
                    bibR.setAthlete(resultStartList.getAthlete());
                    bibR.setRace(activeRace());
                }
                apResultsService.saveBib(bibR);
            }
        }
        return "success" ;
    }
    @GetMapping(value = "/bibNumbers")
    public Map<Athlete, Bib> getBibs(){
        return apResultsService.findByRaceIdMap(activeRace().getId());
    }
    @GetMapping("/exportStartList")
    public String exportCsv(){
        int activeRace = activeRace().getId();
        Date date = activeRace().getStartDate();
        exportStartList.setStartList(apResultsService.findAllByDisciplineRaceAndTypeIdMap(activeRace,"run"));
        exportStartList.setBibs(apResultsService.findByRaceIdMap(activeRace));
        exportStartList.setClubs(clubParticipantsService.findRealClubs(date,date,date));
        return exportStartList.createCsv(activeRace);
    }
}

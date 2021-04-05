package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class Results {
    private RaceService raceService;
    private DisciplineService disciplineService;
    public Results(RaceService raceService,DisciplineService disciplineService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
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
        model.addAttribute("disciplines",disciplineService.findDisciplinesByRaceId(activeRace().getId()));
        return "results/results";
    }
}

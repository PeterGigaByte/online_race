package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

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
    @ModelAttribute("phases")
    public List<Phase> phaseList(){
        return disciplineService.findAllPhasesByRaceId(activeRace().getId());
    }
    @ModelAttribute("disciplines")
    public List<Discipline>  disciplineList(){
        return disciplineService.getAllDisciplinesByRaceId(activeRace().getId());
    }
    @GetMapping("/results")
    public String disciplines(){
        return "results/results";
    }
}

package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class Results {
    private RaceService raceService;
    public Results(RaceService raceService){
        super();
        this.raceService = raceService;
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
    public String disciplines(){
        return "results/results";
    }
}

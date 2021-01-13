package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Date;

@Controller
public class Disciplines {
    private RaceService raceService;
    public Disciplines(RaceService raceService){
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
    @GetMapping("/disciplines")
    public String disciplines(@ModelAttribute("activeRace")
                                          Race activeRace){
        return "disciplines/disciplines";
    }
}

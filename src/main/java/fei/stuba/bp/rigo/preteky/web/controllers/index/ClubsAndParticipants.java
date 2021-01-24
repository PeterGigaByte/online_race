package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClubsAndParticipants {
    private RaceService raceService;
    private DisciplineService disciplineService;
    public ClubsAndParticipants(RaceService raceService,DisciplineService disciplineService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
    }
    @GetMapping("/clubs")
    public String clubs(){
        return "participants&clubs/clubs";
    }
    @GetMapping("/participants")
    public String participants(){
        return "participants&clubs/participants";
    }

}

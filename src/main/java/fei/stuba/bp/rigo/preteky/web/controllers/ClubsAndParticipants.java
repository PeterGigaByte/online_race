package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClubsAndParticipants {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ClubParticipantsService clubParticipantsService;
    public ClubsAndParticipants(RaceService raceService,DisciplineService disciplineService, ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("newClub")
    public Club newClub(){
        return new Club();
    }
    @ModelAttribute("clubs")
    public List<Club> listClubs(){
        return clubParticipantsService.getAllClubs();
    }
    @GetMapping("/clubs")
    public String clubs(){
        return "participants&clubs/clubs";
    }
    @GetMapping("/participants")
    public String participants(){
        return "participants&clubs/participants";
    }
    @PostMapping("/clubs/create")
    public String clubCreated(@ModelAttribute("newClub") Club newClub){
        clubParticipantsService.clubSave(newClub);
        return "redirect:/clubs";
    }
}

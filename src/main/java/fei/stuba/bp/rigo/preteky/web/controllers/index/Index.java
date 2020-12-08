package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class Index {

    private RaceService raceService;

    public Index(RaceService raceService){
        super();
        this.raceService = raceService;
    }

    @ModelAttribute("raceRegister")
    public RaceRegistrationDto raceRegistrationDto(){
        return  new RaceRegistrationDto();
    }

    @ModelAttribute("settingsRegister")
    public SettingsDto settingsDto(){
        return  new SettingsDto();
    }

    @ModelAttribute("trackDto")
    public TrackDto trackDto(){
        return  new TrackDto();
    }

    @ModelAttribute("races")
    public List<Race> races(){
        return  raceService.listRaces();
    }

    @ModelAttribute("activeRace")
    public Race activeRace(){
        return  raceService.getActiveRace();
    }

    @GetMapping("/")
    public  String index(){
        return "index/index";
    }

    @PostMapping("/")
    public String registration(@ModelAttribute("raceRegister")
                               RaceRegistrationDto raceRegistrationDto,
                               @ModelAttribute("settingsRegister")
                                       SettingsDto settingsDto,
                               @ModelAttribute("trackDto")
                                           TrackDto trackDto,
                                @ModelAttribute("races")
                                           List<Race> races){
        raceRegistrationDto.checkForNulls();
        settingsDto.checkForNulls();
        System.out.println(raceRegistrationDto.toString());
        System.out.println(settingsDto.toString());
        System.out.println(trackDto.toString());
        raceService.save(raceRegistrationDto,settingsDto,trackDto);
        System.out.println();

        return "redirect:/";
    }

}

package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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
                                           TrackDto trackDto){
        System.out.println(settingsDto.getCameraType());
        return "redirect:/";
    }
}

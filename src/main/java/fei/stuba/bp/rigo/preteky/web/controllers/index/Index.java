package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

import java.util.List;
import java.util.Optional;


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
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            Race race = new Race();
            race.setRaceName("Žiadny aktívny závod");
            race.setPlace("xxx");
            long millis=System.currentTimeMillis();
            Date date=new Date(millis);
            race.setEndDate(date);
            race.setStartDate(date);
            return race;
        }

    }

    @GetMapping("/")
    public  String index(@ModelAttribute("activeRace")
                                     Race activeRace){
        return "index/index";
    }

    @PostMapping("/createRace")
    public String registration(@ModelAttribute("raceRegister")
                               RaceRegistrationDto raceRegistrationDto,
                               @ModelAttribute("settingsRegister")
                                       SettingsDto settingsDto,
                               @ModelAttribute("trackDto")
                                           TrackDto trackDto,
                                @ModelAttribute("races")
                                           List<Race> races,
                               @ModelAttribute("activeRace")
                                           Race activeRace){
        raceRegistrationDto.checkForNulls();
        settingsDto.checkForNulls();

        raceService.save(raceRegistrationDto,settingsDto,trackDto);
        System.out.println();

        return "redirect:/";
    }

    @GetMapping("/deleteRace/{id}")
    public String deleteRace(@PathVariable Integer id) {
        raceService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/activeRace/{id}")
    public String activeRace(@PathVariable Integer id) {
        Optional<Race> race = raceService.getRace(id);
        race.ifPresent(value -> raceService.changeActivity(value));
        return "redirect:/";
    }
    @GetMapping("/editRace/{id}")
    public String editRace(@PathVariable Integer id,Model model){
        Optional<Race> race = raceService.getRace(id);
        if(race.isPresent()){
        Race raceN = race.get();
        model.addAttribute("race",raceN);
            model.addAttribute("settings",raceN.getSettings());
            model.addAttribute("track",raceN.getSettings().getTrack());
        }

        return "index/editRace";
    }
    @PostMapping("/editRace/{id}")
    public String editRaceAfter(@ModelAttribute("race") Race race,@ModelAttribute("settings") Settings settings,@ModelAttribute("track") Track track){
        race.checkForNulls();
        settings.checkForNulls();
        settings.setTrack(track);
        race.setSettings(settings);
        raceService.save(race);
        return "index/index";
    }

}

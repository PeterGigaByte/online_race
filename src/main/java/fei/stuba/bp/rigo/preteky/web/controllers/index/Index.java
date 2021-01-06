package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
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
            race.setId(-1);
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
        Optional<Race> race = raceService.getRace(id);
        if(race.isPresent()) {
            Race raceN = race.get();
            raceService.deleteRace(raceN);
        }
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
        if(id==-1){
            return "redirect:/";
        }
        Optional<Race> race = raceService.getRace(id);
        if(race.isPresent()){
        Race raceN = race.get();
        model.addAttribute("id",raceN.getId());
        model.addAttribute("race",new RaceRegistrationDto(raceN));
        model.addAttribute("settings",new SettingsDto(raceN.getSettings()));
        model.addAttribute("track",new TrackDto(raceN.getSettings().getTrack()));

        }

        return "index/editRace";
    }
    @PostMapping("/editRace/{id}")
    public String editRaceAfter(@PathVariable Integer id,
                                @ModelAttribute("race") RaceRegistrationDto raceE,
                                @ModelAttribute("settings") SettingsDto settingsE,
                                @ModelAttribute("track") TrackDto trackE) {
        Optional<Race> race = raceService.getRace(id);
        if (race.isPresent()) {
            Race raceN = race.get();
            raceN.setRaceEdit(raceE);
            raceN.getSettings().setSettings(settingsE);
            raceN.getSettings().getTrack().setTrack(trackE);
            raceN.getSettings().checkForNulls();
            raceN.checkForNulls();
            raceService.edit(raceN);
        }
        return "redirect:/";
    }

}

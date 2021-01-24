package fei.stuba.bp.rigo.preteky.web.controllers.index;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@Controller
public class Index {
    private DisciplineService disciplineService;
    private RaceService raceService;

    public Index(RaceService raceService,DisciplineService disciplineService){
        super();
        this.disciplineService = disciplineService;
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
            return raceService.getFakeRace();
        }
    }
    @GetMapping("/contact")
    public String contact(){

        return "contact/contact";
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
        return "redirect:/";
    }

    @GetMapping("/deleteRace/{id}")
    public String deleteRace(@PathVariable Integer id) {
        disciplineService.deleteByRaceId(id);
        raceService.deleteRace(id);
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
        Race race = raceService.findByIdFromRepository(id);
        model.addAttribute("id",race.getId());
        model.addAttribute("race",race);
        model.addAttribute("settings",race.getSettings());
        model.addAttribute("track",race.getSettings().getTrack());
        return "index/editRace";
    }
    @PostMapping("/editRace/{id}/{idSettings}/{idTrack}")
    public String editRaceAfter(@PathVariable Integer id,
                                @PathVariable Integer idSettings,
                                @PathVariable Integer idTrack,
                                @ModelAttribute("race") Race race,
                                @ModelAttribute("settings") Settings settings,
                                @ModelAttribute("track") Track track) {
        settings.setId(idSettings);
        track.setId(idTrack);
        race.setSettings(settings);settings.setTrack(track);
        race.checkForNulls();settings.checkForNulls();
        raceService.editRealRace(race,settings,track);
        return "redirect:/";
    }

}

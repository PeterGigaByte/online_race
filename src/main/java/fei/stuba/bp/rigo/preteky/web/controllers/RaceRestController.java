package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/races")
public class RaceRestController {
    private RaceService raceService;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public RaceRestController(RaceService raceService) {
        this.raceService = raceService;
    }
    @GetMapping(value = "/all")
    public List<Race> getRacesResource(){return raceService.listRaces();
    }

    @PostMapping(value="/save")
    public String saveRace(@RequestBody ObjectNode jsonNodes) throws ParseException {
        Date startDate = new Date (format.parse(jsonNodes.get("startDate").asText()).getTime());
        Date endDate = new Date (format.parse(jsonNodes.get("endDate").asText()).getTime());
        Track track = new Track(jsonNodes.get("numberOfTracks").asInt());
        Settings settings = new Settings(
                jsonNodes.get("cameraType").asText(),
                jsonNodes.get("raceType").asInt(),
                jsonNodes.get("typeScoring").asText(),
                jsonNodes.get("outCompetition").asInt(),
                jsonNodes.get("reactions").asInt(),
                track
                );
        Race race = new Race(
                0,
                jsonNodes.get("raceName").asText(),
                jsonNodes.get("place").asText(),
                jsonNodes.get("organizer").asText(),
                jsonNodes.get("resultsManager").asText(),
                jsonNodes.get("phone").asText(),
                startDate,
                endDate,
                jsonNodes.get("raceType").asInt(),
                jsonNodes.get("note").asText(),
                jsonNodes.get("director").asText(),
                jsonNodes.get("arbitrator").asText(),
                jsonNodes.get("technicalDelegate").asText(),
                settings
        );
        raceService.save(race);
        return "Post Successfully";
    }
}

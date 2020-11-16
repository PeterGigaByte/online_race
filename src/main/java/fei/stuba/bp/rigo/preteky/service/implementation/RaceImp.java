package fei.stuba.bp.rigo.preteky.service.implementation;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;
import fei.stuba.bp.rigo.preteky.repository.RaceRepository;
import fei.stuba.bp.rigo.preteky.repository.SettingsRepository;
import fei.stuba.bp.rigo.preteky.repository.TrackRepository;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RaceImp implements RaceService {
    @Autowired
    private RaceRepository raceRepository;
    private SettingsRepository settingsRepository;
    private TrackRepository trackRepository;

    public RaceImp(RaceRepository raceRepository,SettingsRepository settingsRepository,TrackRepository trackRepository){
        super();
        this.raceRepository = raceRepository;
        this.settingsRepository=settingsRepository;
        this.trackRepository=trackRepository;
    }
    @Override
    public Race save(RaceRegistrationDto raceRegistrationDto, SettingsDto settingsDto, TrackDto trackDto){

        Track track = new Track(trackDto.getNumberOfTracks(),trackDto.getOne(),trackDto.getTwo(),trackDto.getThree(),trackDto.getFour(),trackDto.getFive(),trackDto.getSix(),
                trackDto.getSeven(),trackDto.getEight(),trackDto.getNine(),trackDto.getTen(),trackDto.getTypeTrack());

        Settings settings = new Settings(settingsDto.getCameraType(),settingsDto.getTypeRace(),settingsDto.getTypeScoring(),settingsDto.getOutCompetition(),settingsDto.getReactions(),
                track);

        Race race = new Race(raceRegistrationDto.getActivity(),raceRegistrationDto.getRaceName(),raceRegistrationDto.getPlace(),raceRegistrationDto.getOrganizer(),raceRegistrationDto.getResultsManager(),
                raceRegistrationDto.getPhone(),raceRegistrationDto.getStartDate(),raceRegistrationDto.getEndDate(),raceRegistrationDto.getRaceType(),raceRegistrationDto.getNote(),
                raceRegistrationDto.getDirector(),raceRegistrationDto.getArbitrator(),raceRegistrationDto.getTechnicalDelegate(),settings
        );

        settingsRepository.save(settings);
        trackRepository.save(track);
        return raceRepository.save(race);
    }


    @Override
    public Race edit(Race race){

        return raceRepository.save(race);
    }

    @Override
    public List<Race> listRaces() {
        return raceRepository.findAll(Sort.by(Sort.Direction.ASC,"fromDay"));

    }

    @Override
    public Optional<Race> getRace(int id) {
        return raceRepository.findById(id);
    }

}

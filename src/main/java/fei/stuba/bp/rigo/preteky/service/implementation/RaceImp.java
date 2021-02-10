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


import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RaceImp implements RaceService {
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private TrackRepository trackRepository;

    public RaceImp(RaceRepository raceRepository,SettingsRepository settingsRepository,TrackRepository trackRepository){
        super();
        this.raceRepository = raceRepository;
        this.settingsRepository=settingsRepository;
        this.trackRepository=trackRepository;
    }
    @Override
    public void save(RaceRegistrationDto raceRegistrationDto, SettingsDto settingsDto, TrackDto trackDto){

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
        raceRepository.save(race);
    }

    @Override
    public void deleteRace(Integer id){
        Optional<Race> race = raceRepository.findById(id);
        if(race.isPresent()){
            Integer settingsId=race.get().getSettings().getId();
            raceRepository.deleteById(id);
            settingsRepository.deleteById(settingsId);

        }
    }
    @Override
    public Race getRaceById(Integer id){
        return raceRepository.getOne(id);
    }

    @Override
    public void edit(Race race){
        Settings settings = race.getSettings();
        Track track = settings.getTrack();
        settingsRepository.save(settings);
        trackRepository.save(track);
        raceRepository.save(race);
    }

    @Override
    public List<Race> listRaces() {
        return raceRepository.findAll(Sort.by(Sort.Direction.DESC,"startDate"));
    }

    @Override
    public Optional<Race> getRace(int id) {
        return raceRepository.findById(id);
    }

    @Override
    public List<Race> getActiveRace() {
        return raceRepository.findRegisteredUserByActivity(1);
    }
    @Override
    public Race getFakeRace(){
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
    @Override
    public void changeActivity(Race race) {
        List<Race> list = getActiveRace();
        if(list.size()>0){
            for (Race raceL:list)
                {
                    if(raceL.getActivity()==1)
                    {
                         raceL.setActivity(0);
                         raceRepository.save(raceL);
                    }
                }
        }
        race.setActivity(1);
        raceRepository.save(race);
    }
    @Override
    public Race findByIdFromRepository(Integer id){
        return raceRepository.findRaceById(id);
    }
    @Override
    public void editRealRace(Race race,Settings settings,Track track){
        raceRepository.save(race);settingsRepository.save(settings);trackRepository.save(track);
    }

}

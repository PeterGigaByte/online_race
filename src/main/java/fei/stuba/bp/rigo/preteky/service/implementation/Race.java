package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Race implements fei.stuba.bp.rigo.preteky.service.service.Race {
    @Autowired
    private RaceRepository raceRepository;

    public Race(RaceRepository raceRepository){
        super();
        this.raceRepository = raceRepository;
    }


}

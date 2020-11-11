package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Track implements fei.stuba.bp.rigo.preteky.service.service.Track {
    @Autowired
    private TrackRepository trackRepository;

    public Track(TrackRepository trackRepository){
        super();
        this.trackRepository = trackRepository;
    }
}

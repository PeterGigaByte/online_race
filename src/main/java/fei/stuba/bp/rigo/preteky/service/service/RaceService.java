package fei.stuba.bp.rigo.preteky.service.service;


import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.web.dto.RaceRegistrationDto;
import fei.stuba.bp.rigo.preteky.web.dto.SettingsDto;
import fei.stuba.bp.rigo.preteky.web.dto.TrackDto;

import java.util.List;
import java.util.Optional;

public interface RaceService {
    Race save(RaceRegistrationDto raceRegistrationDto, SettingsDto settingsDto, TrackDto trackDto);
    Race edit(Race race);
    List<Race> listRaces();
    Optional<Race> getRace(int id);
    Race getActiveRace();
}

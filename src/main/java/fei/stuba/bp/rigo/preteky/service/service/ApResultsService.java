package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;

import java.util.List;
import java.util.Map;

public interface ApResultsService {
    public List<ResultStartList> findResultStartListByRaceId(int id);
    Map<Athlete, Bib> findByRaceIdMap(int id);
    List<ResultStartList> findByAthleteIdAndDisciplineId(int idAthlete, int idDiscipline);
    Bib findByRaceIdAndAthleteId(int raceId,int athleteId);
    void saveBib(Bib bib);
    void saveResultStartList(ResultStartList resultStartList);
    List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineId(int idRace,int idDiscipline);
}

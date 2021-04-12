package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
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
    Map<Athlete, ResultStartList> findAllByDisciplineRaceIdMap(int id);
    Map<Discipline,List<ResultStartList>> mapResultsToDiscipline(int activeRace);
    Map<Discipline, List<ResultStartList>> findAllByDisciplineRaceAndTypeIdMap(int idRace, String type);
    ResultStartList findById(int id);
    Bib findByRaceIdAndBib(int id, int bib);
    void deleteStartList(int id);
    List <ResultStartList> findResultStartListByDisciplineId(int disciplineId);
    List<ResultStartList> findAllByDisciplineIdOrderByResultPerformanceAsc(int id);
}

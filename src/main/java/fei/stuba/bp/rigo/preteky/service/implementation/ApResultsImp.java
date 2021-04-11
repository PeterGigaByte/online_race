package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.repository.BibRepository;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.repository.ResultStartListRepository;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApResultsImp implements ApResultsService {
    @Autowired
    private ResultStartListRepository resultStartListRepository;
    @Autowired
    private BibRepository bibRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    public ApResultsImp(ResultStartListRepository resultStartListRepository,BibRepository bibRepository) {
        this.resultStartListRepository = resultStartListRepository;
        this.bibRepository=bibRepository;
    }
    @Override
    public List<ResultStartList> findResultStartListByRaceId(int id){
        return resultStartListRepository.findAllByDisciplineRaceId(id);
    }

    @Override
    public Map<Athlete, Bib> findByRaceIdMap(int id) {
        return bibRepository.findByRaceIdMap(id);
    }

    @Override
    public List<ResultStartList> findByAthleteIdAndDisciplineId(int idAthlete, int idDiscipline) {
        return resultStartListRepository.findByAthleteIdAndDisciplineId(idAthlete,idDiscipline);
    }

    @Override
    public Bib findByRaceIdAndAthleteId(int raceId, int athleteId) {
        return bibRepository.findByRaceIdAndAthleteId(raceId,athleteId);
    }
    @Override
    public void saveBib(Bib bib) {
        bibRepository.save(bib);
    }
    @Override
    public void saveResultStartList(ResultStartList resultStartList) {
        resultStartListRepository.save(resultStartList);
    }

    @Override
    public List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineId(int idRace, int idDiscipline) {
        return resultStartListRepository.findAllByDisciplineRaceIdAndDisciplineId(idRace,idDiscipline);
    }

    @Override
    public Map<Athlete, ResultStartList> findAllByDisciplineRaceIdMap(int id) {
       return resultStartListRepository.findAllByDisciplineRaceIdMap(id);
    }

    @Override
    public Map<Discipline, List<ResultStartList>> mapResultsToDiscipline(int activeRace) {
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByRaceIdOrderByDisciplineTime(activeRace);
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplines) {
            map.put(discipline,findAllByDisciplineRaceIdAndDisciplineId(activeRace,discipline.getId()));
        }
        return map;
    }

    @Override
    public Map<Discipline, List<ResultStartList>> findAllByDisciplineRaceAndTypeIdMap(int idRace, String type) {
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByRaceIdAndDisciplineTypeOrderByDisciplineTime(idRace,type);
        Map<Discipline, List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplines) {
            map.put(discipline,findAllByDisciplineRaceIdAndDisciplineId(idRace,discipline.getId()));
        }
        return map;
    }

    @Override
    public ResultStartList findById(int id) {
       return resultStartListRepository.findById(id);
    }

    @Override
    public Bib findByRaceIdAndBib(int id, int bib) {
        return bibRepository.findByRaceIdAndBib(id,bib);
    }
    @Override
    public void deleteStartList(int id) {
        Discipline discipline = resultStartListRepository.findById(id).getDiscipline();
        discipline.setParticipants(discipline.getParticipants()-1);
        disciplineRepository.save(discipline);
        resultStartListRepository.deleteById(id);
    }
    @Override
    public List <ResultStartList> findResultStartListByDisciplineId(int disciplineId){
        return resultStartListRepository.findResultStartListByDisciplineIdOrderByStartPerformanceAsc(disciplineId);
    }
}

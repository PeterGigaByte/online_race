package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.repository.BibRepository;
import fei.stuba.bp.rigo.preteky.repository.ClubTransferRepository;
import fei.stuba.bp.rigo.preteky.repository.ResultStartListRepository;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApResultsImp implements ApResultsService {
    @Autowired
    private ResultStartListRepository resultStartListRepository;
    @Autowired
    private BibRepository bibRepository;

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
}

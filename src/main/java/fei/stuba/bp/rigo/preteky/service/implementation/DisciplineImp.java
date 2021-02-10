package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.repository.PhaseRepository;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DisciplineImp implements DisciplineService {
    @Autowired
    private PhaseRepository phaseRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;

    public DisciplineImp(DisciplineRepository disciplineRepository,PhaseRepository phaseRepository){
        super();
        this.phaseRepository=phaseRepository;
        this.disciplineRepository=disciplineRepository;
    }
    @Override
    public void deleteDiscipline(Discipline discipline){
        disciplineRepository.delete(discipline);
    }
    @Override
    public void saveDiscipline(Discipline discipline){
         disciplineRepository.save(discipline);
    }
    @Override
    public Discipline findDisciplineById(Integer disciplineId){
        return disciplineRepository.findDisciplineById(disciplineId);
    }
    @Override
    public List<Discipline> getAllDisciplinesByRaceId(Integer raceId){
        return disciplineRepository.findDisciplinesByRaceIdOrderByTimeAsc(raceId);
    }
    @Override
    public void deleteByRaceId(Integer raceId){
        disciplineRepository.deleteAllByRaceId(raceId);
    }
    @Override
    public void savePhase(Phase phase){
        phaseRepository.save(phase);
    }
    @Override
    public void removePhase(Integer id,Integer idPhase){
        Discipline discipline = disciplineRepository.findDisciplineById(id);
        discipline.getPhases().removeIf(phase -> phase.getId().equals(idPhase));
        phaseRepository.deleteById(idPhase);
    }
    @Override
    public Phase findPhaseById(Integer phaseId){
        return phaseRepository.findPhaseById(phaseId);
    }
    @Override
    public List<Phase> findAllPhasesByRaceId(Integer id){
        return phaseRepository.findPhasesByDisciplineRaceId(id);
    }
    @Override
    public List<Phase> findPhasesByRaceIdAndDisciplineType(Integer id,Integer disciplineType){
        return phaseRepository.findPhasesByDisciplineRaceIdAndDisciplineDisciplineType(id,disciplineType);
    }
    @Override
    public List<Phase> findPhasesByDisciplineIdAndPhaseName(Integer id, String phaseName){
        return phaseRepository.findPhasesByDisciplineIdAndPhaseName(id, phaseName);
    }
    @Override
    public void refreshCameraId(Integer id,Integer disciplineType){
        List<Phase> phases = findPhasesByRaceIdAndDisciplineType(id,disciplineType);
        int index = 1;
        for (Phase phase: phases) {
            phase.setCameraId(index);
            disciplineRepository.save(phase.getDiscipline());
            index++;
        }
    }
    @Override
    public void refreshPhaseNumber(Integer id, String phaseName){
        List<Phase> phases =findPhasesByDisciplineIdAndPhaseName(id,phaseName);
        int index = 1;
        for (Phase phase: phases) {
            phase.setPhaseNumber(index);
            disciplineRepository.save(phase.getDiscipline());
            index++;
        }
    }

}

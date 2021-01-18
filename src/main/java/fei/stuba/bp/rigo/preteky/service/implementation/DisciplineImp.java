package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DisciplineImp implements DisciplineService {
    @Autowired
    private DisciplineRepository disciplineRepository;

    public DisciplineImp(DisciplineRepository disciplineRepository){
        super();
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
        return disciplineRepository.findDisciplinesByRaceId(raceId);
    }
    @Override
    public void deleteByRaceId(Integer raceId){
        disciplineRepository.deleteAllByRaceId(raceId);
    }


}

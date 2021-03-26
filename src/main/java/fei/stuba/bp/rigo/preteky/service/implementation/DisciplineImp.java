package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.repository.PhaseRepository;
import fei.stuba.bp.rigo.preteky.repository.QualificationSettingsDisciplineRepository;
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
    @Autowired
    private QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository;
    public DisciplineImp(DisciplineRepository disciplineRepository,PhaseRepository phaseRepository, QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository){
        super();
        this.phaseRepository=phaseRepository;
        this.disciplineRepository=disciplineRepository;
        this.qualificationSettingsDisciplineRepository= qualificationSettingsDisciplineRepository;
    }


    @Override
    public List<Discipline> findDisciplinesByRaceId(Integer id) {
        return disciplineRepository.findDisciplinesByRaceId(id);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline) {
        return disciplineRepository.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(disciplineName,category,idRace,idDiscipline);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName, String category, int idRace, int idDiscipline, String phaseName, int phaseNumber) {
        return disciplineRepository.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(disciplineName,category,idRace,idDiscipline,phaseName,phaseNumber);
    }

    @Override
    public List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id) {
        return disciplineRepository.findDisciplinesByRaceIdOrderByCameraIdDesc(id);
    }

    @Override
    public void saveDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
    }
    @Override
    public void saveQualificationSettings(QualificationSettings qualificationSettings){
        qualificationSettingsDisciplineRepository.save(qualificationSettings);
    }

    @Override
    public Discipline findDisciplineById(int id) {
        return disciplineRepository.findDisciplinesById(id);
    }

}

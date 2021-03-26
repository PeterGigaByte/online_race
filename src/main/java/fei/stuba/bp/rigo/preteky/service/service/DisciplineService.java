package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;

import java.util.List;

public interface DisciplineService {
    List<Discipline> findDisciplinesByRaceId(Integer id);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName,String category,int idRace, int idDiscipline,String phaseName,int phaseNumber);
    List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id);
    void saveDiscipline(Discipline discipline);
    void saveQualificationSettings(QualificationSettings qualificationSettings);
    Discipline findDisciplineById(int id);
}

package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer>, JpaSpecificationExecutor<Discipline> {
    List<Discipline> findDisciplinesByRaceId(Integer id);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName,String category,int idRace, int idDiscipline,String phaseName,int phaseNumber);
    List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id);
    Discipline findDisciplinesById(int id);
}
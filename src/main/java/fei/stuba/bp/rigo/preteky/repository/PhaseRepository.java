package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, Integer>, JpaSpecificationExecutor<Phase> {
    List<Phase> findPhasesByDisciplineId(Integer disciplineId);
    Phase findPhaseById(Integer phaseId);
    void deleteAllByDisciplineId(Integer disciplineId);
    List<Phase> findPhasesByDisciplineRaceId(Integer id);
    List<Phase> findPhasesByDisciplineRaceIdAndDisciplineDisciplineType(Integer id,Integer disciplineType);
    List<Phase> findPhasesByDisciplineIdAndPhaseName(Integer id,String phaseName);
    void deleteById(Integer id);
}
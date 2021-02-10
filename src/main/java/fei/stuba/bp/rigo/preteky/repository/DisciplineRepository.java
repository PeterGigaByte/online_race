package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer>, JpaSpecificationExecutor<Discipline> {
    List<Discipline> findDisciplinesByRaceIdOrderByTimeAsc(Integer raceId);
    Discipline findDisciplineById(Integer disciplineId);
    void deleteAllByRaceId(Integer raceId);
}
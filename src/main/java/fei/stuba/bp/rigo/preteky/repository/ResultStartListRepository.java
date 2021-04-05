package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ResultStartListRepository extends JpaRepository<ResultStartList, Integer>, JpaSpecificationExecutor<ResultStartList> {
    List<ResultStartList> findAllByDisciplineRaceId(int id);
    List<ResultStartList> findAllByDisciplineId(int id);
    List<ResultStartList> findByAthleteIdAndDisciplineId(int idAthlete, int idDiscipline);
    List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineId(int idRace,int idDiscipline);
}
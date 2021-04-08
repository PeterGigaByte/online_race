package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.*;
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
    default Map<Athlete, ResultStartList> findAllByDisciplineRaceIdMap(int id) {
        return findAllByDisciplineRaceId(id).stream().collect(Collectors.toMap(ResultStartList::getAthlete, v -> v));
    }

    List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineDisciplineType(int idRace,String type);

}
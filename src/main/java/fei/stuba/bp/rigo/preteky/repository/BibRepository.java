package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface BibRepository extends JpaRepository<Bib, Integer>, JpaSpecificationExecutor<Bib> {
    List<Bib> findByRaceId(int id);

    default Map<Athlete, Bib> findByRaceIdMap(int id) {
        return findByRaceId(id).stream().collect(Collectors.toMap(Bib::getAthlete, v -> v));
    }
    Bib findByRaceIdAndAthleteId(int raceId,int athleteId);
}
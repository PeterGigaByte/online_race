package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttemptRepository extends JpaRepository<Attempt, Integer>, JpaSpecificationExecutor<Attempt> {

}
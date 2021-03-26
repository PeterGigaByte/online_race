package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QualificationSettingsDisciplineRepository extends JpaRepository<QualificationSettings, Integer>, JpaSpecificationExecutor<QualificationSettings> {

}
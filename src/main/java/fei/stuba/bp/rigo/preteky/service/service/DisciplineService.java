package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Phase;

import java.util.List;

public interface DisciplineService {
    void deleteDiscipline(Discipline discipline);
    void saveDiscipline(Discipline discipline);
    Discipline findDisciplineById(Integer disciplineId);
    List<Discipline> getAllDisciplinesByRaceId(Integer raceId);
    void deleteByRaceId(Integer raceId);
    void savePhase(Phase phase);
    void removePhase(Integer id,Integer idPhase);
    Phase findPhaseById(Integer phaseId);
    List<Phase> findAllPhasesByRaceId(Integer id);
    List<Phase> findPhasesByRaceIdAndDisciplineType(Integer id,Integer disciplineType);
    List<Phase> findPhasesByDisciplineIdAndPhaseName(Integer id, String phaseName);
    void refreshCameraId(Integer id,Integer disciplineType);
    void refreshPhaseNumber(Integer id, String phaseName);
}

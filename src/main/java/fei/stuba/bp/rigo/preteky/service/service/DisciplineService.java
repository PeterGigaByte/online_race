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
    void removePhase(Phase phase);
    Phase findPhaseById(Integer phaseId);
}

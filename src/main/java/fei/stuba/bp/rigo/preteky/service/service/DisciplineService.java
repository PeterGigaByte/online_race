package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;

import java.util.List;

public interface DisciplineService {
    void deleteDiscipline(Discipline discipline);
    void saveDiscipline(Discipline discipline);
    Discipline findDisciplineById(Integer disciplineId);
    List<Discipline> getAllDisciplinesByRaceId(Integer raceId);
    void deleteByRaceId(Integer raceId);
}

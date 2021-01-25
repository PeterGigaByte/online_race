package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.Participant;

import java.util.List;

public interface ClubParticipantsService {
    void participantSave(Participant participant);
    void clubSave(Club club);
    List<Club> getAllClubs();
    Club findClubById(Integer id);
}

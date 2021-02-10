package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.Participant;
import fei.stuba.bp.rigo.preteky.repository.ClubRepository;
import fei.stuba.bp.rigo.preteky.repository.ParticipantRepository;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClubParticipantsImp implements ClubParticipantsService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ParticipantRepository participantRepository;

    public ClubParticipantsImp(ClubRepository clubRepository,ParticipantRepository participantRepository){
        super();
        this.clubRepository=clubRepository;
        this.participantRepository=participantRepository;
    }
    @Override
    public void clubSave(Club club){
        clubRepository.save(club);
    }
    @Override
    public void participantSave(Participant participant){
        participantRepository.save(participant);
    }
    @Override
    public List<Club> getAllClubs(){
        return clubRepository.findAll();
    }
    @Override
    public  Club findClubById(Integer id){
        return clubRepository.findClubById(id);
    }
}

package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.TournamentService;
import be.TFTIC.Tournoi.dal.repositories.TeamRepository;
import be.TFTIC.Tournoi.dal.repositories.TournamentRepository;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;
import be.TFTIC.Tournoi.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The tournament with id " + id + " not found"));
    }
// TODO DELETE 
    @Override
    public Long create(Tournament tournament) {
        tournament.setDateDebut(LocalDateTime.now());
        Tournament savetournament = tournamentRepository.save(tournament);
        //TODO REFAC tournamentParticipantMap.put(tournament.getTournamentId(), new ArrayList<>());
        return savetournament.getTournamentId();
    }

    @Override
    public void update(Long id, Tournament tournament) {
        Tournament existingTournament = getById(id);
        existingTournament.setName(tournament.getName());
        existingTournament.setLocation(tournament.getLocation());
        existingTournament.setNbPlace(tournament.getNbPlace());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void tournamentFinish(Long id) {
        Tournament existingTournament = getById(id);
        existingTournament.setDateFin(LocalDateTime.now());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void delete(Long id) {
        Tournament existingTournament = getById(id);
        tournamentRepository.delete(existingTournament);
    }

    @Override
    public void inscription(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tournament tournament = getById(id);
        List<Team> participants = tournament.getParticipant();

        Team existingTeam = teamRepository.findSoloTeamByUserId(user.getId());
        Team userTeam;
        if(existingTeam != null){
            userTeam = existingTeam;
        } else {
            userTeam = new Team();
            userTeam.setTeamId(user.getUsername().substring(0,2).toUpperCase()+ user.getId());
            userTeam.setName(user.getUsername() + "'s team");
            userTeam.setUsers(Collections.singletonList(user));
            teamRepository.save(userTeam);
        }

        if(participants.size() > tournament.getNbPlace() - 1){
            throw new RuntimeException("Tournament full");
        }
        for(Team team : participants){
            if(team.getTeamId().equals(userTeam.getTeamId())){
                throw new RuntimeException("User alrady register");
            }
        }

        participants.add(userTeam);
        tournamentRepository.save(tournament);
    }

    @Override
    public List<Team> getParticipant(Long tournamentId){
        Tournament tournament = tournamentRepository.findTeamByTournament(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        return tournament.getParticipant();
    }
}

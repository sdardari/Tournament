package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchFactory;
import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.bll.services.TeamService;
import be.TFTIC.Tournoi.bll.services.TournamentService;
import be.TFTIC.Tournoi.dal.repositories.TeamRepository;
import be.TFTIC.Tournoi.dal.repositories.TournamentRepository;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.matchDTO.CreateMatchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchService matchService;
    private final TeamService teamService;
    private List<String> nextTurnTeam = new ArrayList<>();
    private Integer nbMatch = 0;

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The tournament with id " + id + " not found"));
    }

    @Override
    public Long create(Tournament tournament) {
        if(tournament.getNbPlace()<4){
            throw new RuntimeException("Pas assez de place dans le tournoi");
        }
        if(tournament.getNbPlace()>32){
            throw new RuntimeException("Pas assez de place dans le tournoi");
        }
        if(tournament.getNbPlace()%4 != 0){
            throw new RuntimeException("il faut pouvoir créer un nombre de place divisible par 4!");
        }
        tournament.setDateDebut(LocalDateTime.now());
        Tournament savetournament = tournamentRepository.save(tournament);
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
            userTeam.setTeamId(Long.toString(user.getId()));
            userTeam.setName(user.getUsername().substring(0,2).toUpperCase()+ user.getId());
            //userTeam.setUsers(Collections.singletonList(user));
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
        if(participants.size() == tournament.getNbPlace()){
            startTournament(participants, id);
        }
        tournamentRepository.save(tournament);
    }

    @Override
    public List<Team> getParticipant(Long tournamentId){
        Tournament tournament = tournamentRepository.findTeamByTournament(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
        return tournament.getParticipant();
    }

    public List<String> createTeam(List<Team> users){
        List<String> teams = new ArrayList<>();

        for (int i = 0; i < users.size(); i += 2) {
            teams.add(users.get(i).getTeamId() + "_" + users.get(i+1).getTeamId());
        }
        return teams;
    }

    public void startTournament(List<Team> participant, Long id){
        List<String> teams = createTeam(participant);
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The tournament with id " + id + " not found"));
        createMatch(teams, tournament);
    }

    public void createMatch(List<String> teams, Tournament tournament){
        if(nbMatch.equals(1)){
            winnerTournament(teams, tournament);
        } else {
            nbMatch = 0;
            for (int i = 0; i < teams.size(); i += 2) {
                nbMatch++;
                Match match = CreateMatchForm.toEntity(teams.get(i), teams.get(i + 1), tournament);
                matchService.save(match);
            }
        }
    }
    @Override
    public void nextTurn(String teamId, Tournament tournament){
        nextTurnTeam.add(teamId);
        createMatch(nextTurnTeam, tournament);
    }
    @Override
    public void winnerTournament(List<String> winner, Tournament tournament){
        tournament.setWinner(winner.getFirst());
        tournamentRepository.save(tournament);
    }

    @Override
    public String getWinner(Long id){
        Tournament tournament = getById(id);
        String winner = tournament.getWinner();
        return winner;
    }
}

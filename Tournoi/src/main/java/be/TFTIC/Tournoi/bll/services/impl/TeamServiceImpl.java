package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.TeamService;
import be.TFTIC.Tournoi.dal.repositories.TeamRepository;
import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team getTeamById(String id) {
        StringBuilder sb = new StringBuilder();
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(sb.append("Team ").append(id).append(" not found").toString()));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team createTeam(Team team) {
        team.setRanking(new Ranking());
        return teamRepository.save(team);
    }

    @Override
    public void updateTeam(String id, Team team) {
        Team existingTeam = getTeamById(id);
        existingTeam.setName(team.getName());
        teamRepository.save(existingTeam);
    }

    @Override
    public void deleteTeam(String id) {
        Team existingTeam = getTeamById(id);
        teamRepository.delete(existingTeam);
    }
}

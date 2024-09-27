package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.TeamService;
import be.TFTIC.Tournoi.dal.repositories.TeamRepository;
import be.TFTIC.Tournoi.dl.entities.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Team getTeamById(String id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team createTeam(Team team) {
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

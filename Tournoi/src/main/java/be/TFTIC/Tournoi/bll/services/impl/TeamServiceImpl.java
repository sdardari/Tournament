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
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team with id " + id + " not found"));
    }

    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        return teams;
    }

    @Override
    public Team createTeam(Team team) {
        return null;
    }

    @Override
    public Team updateTeam(Team team) {
        return null;
    }

    @Override
    public void deleteTeam(Long id) {

    }
}

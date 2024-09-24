package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Team;

import java.util.List;

public interface TeamService {

    Team getTeamById(String id);
    List<Team> getAllTeams();
    Team createTeam(Team team);
    Team updateTeam(Team team);
    void deleteTeam(Long id);

}

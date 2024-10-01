package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.Team;

import java.util.List;

public interface TeamService {

    Team getTeamById(String id);
    List<Team> getAllTeams();
    Team createTeam(Team team);
    void updateTeam(String id, Team team);
    void deleteTeam(String id);

}

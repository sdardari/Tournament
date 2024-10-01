package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;

import java.util.List;

public interface MatchFactory {
    void createMatch(List<Team> teams, Tournament tournament);
    String determineMatchWinner(Match match);
}

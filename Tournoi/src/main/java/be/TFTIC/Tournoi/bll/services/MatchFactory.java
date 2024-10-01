package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;

import java.util.List;

public interface MatchFactory {
    String determineMatchWinner(Match match);
}

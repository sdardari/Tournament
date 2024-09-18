package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Match;

import java.util.List;

public interface MatchService {

    // region CRUD Match
    List<Match> getAll();
    Match getById(Long id);
    Match createMatch(Match match);
    String creatTeam(Long player1, Long player2);
    void update(Long id, Match match);
    void delete(Long id);
    // endregion

    // region Logic Match
    String determineMatchWinner(Match match);
    String determinerSetWinner(int scoreTeam1, int scoreTeam2);
    // endregion




}

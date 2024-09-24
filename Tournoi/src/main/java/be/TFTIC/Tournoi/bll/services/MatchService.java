package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;

import java.util.List;

public interface MatchService {

    // region CRUD Match
    List<Match> getAll();
    Match getById(Long id);
//    Match createMatch(MatchForm matchForm);
//    void update(Long id, MatchForm matchForm);
    void delete(Long id);
    // endregion

    // region Logic Match
    String determineMatchWinner(Match match);
    String determinerSetWinner(int scoreTeam1, int scoreTeam2);

    // endregion
    Match save(Match match);

}

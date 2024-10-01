package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;


import java.util.List;

public interface TournamentService {

    List<Tournament> getAll();
    Tournament getById(Long id);
    Long create(Tournament tournament);
    void update(Long id, Tournament tournament);
    void delete(Long id);
    void tournamentFinish (Long id);
    void inscription (Long id);
    List<Team> getParticipant(Long id);
    void nextTurn(String teamId, Tournament tournament);
    String getWinner(Long id);
    void winnerTournament(List<String> winner, Tournament tournament);
}

package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Tournament;

import java.util.List;

public interface TournamentService {

    List<Tournament> getAll();
    Tournament getById(Long id);
    Long create(Tournament tournament);
    void update(Long id, Tournament tournament);
    void delete(Long id);
    void tournamentFinish (Long id);
}

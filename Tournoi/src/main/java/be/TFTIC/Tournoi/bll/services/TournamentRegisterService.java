package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.*;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;

import java.util.List;

public interface TournamentRegisterService {

    TournamentRegisterTemp save(TournamentRegisterTemp tournamentRegisterTemp);
    List<TournamentRegisterTemp> findAll();
    TournamentRegisterTemp findById(Long id);
    void delete(TournamentRegisterTemp tournamentRegisterTemp);
    void deleteById(Long id);
    void update(TournamentRegisterTemp tournamentRegisterTemp);
    List<TournamentRegisterTemp> findAllByTournamentId(Long tournamentId);
    TournamentRegisterTemp inscriptionSolo(Long userId, TypeTournament typeTournament);
    TournamentRegisterTemp inscriptionTeam(Long teamId, TypeTournament typeTournament);
    TournamentRegisterTemp inscriptionClan(Long clanId, TypeTournament typeTournament);
    TournamentRegisterTemp matchMakingTournament(Long userId, Long teamId, Long clanId, Division division, TypeTournament typeTournament);
    void checkIsCompleted(Tournament tournament);
}

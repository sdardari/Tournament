package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.*;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;


import java.util.List;
import java.util.Set;

public interface TournamentService {

    List<Tournament> getAll();
    Tournament getById(Long id);
    Tournament createNewTournament(Division division, TypeTournament typeTournament);
    void update(Long id, Tournament tournament);
    void delete(Long id);
    List<Tournament> getTournamentsByDivisionAndType(Division division, TypeTournament typeTournament);
    void startTournament(Tournament tournament);
    List<String> participants(Long tournamentId);
    void tournamentFinish(Long id);
    List<User> getTwoAvailableUsersFromClan(Clan clan, Set<Long> assignedUsers);
    void createMatch(List<String> teams, Tournament tournament);
    User findAnotherAvailableUser(List<TournamentRegisterTemp> participantsRegister, Set<Long> assignedUsers);
    void nextTurn(String teamId, Tournament tournament);
    String getWinner(Long id);
    void winnerTournament(List<String> winner, Tournament tournament);
}

package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.*;
import be.TFTIC.Tournoi.dal.repositories.TournamentRegisterTempRepository;
import be.TFTIC.Tournoi.dal.repositories.TournamentRepository;
import be.TFTIC.Tournoi.dl.entities.*;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import be.TFTIC.Tournoi.pl.models.matchDTO.CreateMatchForm;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService, TournamentRegisterService {

    private final TournamentRepository tournamentRepository;

    private final MatchService matchService;
    private final UserService userService;
    private final RankingService rankingService;
    private final TournamentRegisterTempRepository tournamentRegisterTempRepository;
    private final TeamService teamService;
    private final ClanService clanService;


    //region UTILS
    private List<String> nextTurnTeam = new ArrayList<>();
    private Integer nbMatch = 0;

    @Override
    public List<Tournament> getAll() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("The tournament with id " + id + " not found"));
    }
//region Create Tournament
    @Override
    public Tournament createNewTournament(Division division, TypeTournament typeTournament) {
        Tournament tournament = new Tournament();
        tournament.setName("Tournois :" + division + " - " + typeTournament);
        tournament.setLocation("Default Location");
        if(typeTournament.ordinal() == 0) {
            tournament.setNbPlace(8);
        } else if (typeTournament.ordinal() == 1) {
            tournament.setNbPlace(32);
        }else {
            tournament.setNbPlace(64);
        }
        tournament.setDivision(division);
        tournament.setTypeTournament(typeTournament);
        tournament.setDateDebut(LocalDateTime.now());

        return tournamentRepository.save(tournament);
    }

    @Override
    public void update(Long id, Tournament tournament) {
        Tournament existingTournament = getById(id);
        existingTournament.setName(tournament.getName());
        existingTournament.setLocation(tournament.getLocation());
        existingTournament.setTypeTournament(tournament.getTypeTournament());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void tournamentFinish(Long id) {
        Tournament existingTournament = getById(id);
        existingTournament.setDateFin(LocalDateTime.now());
        tournamentRepository.save(existingTournament);
    }

    @Override
    public void delete(Long id) {
        Tournament existingTournament = getById(id);
        tournamentRepository.delete(existingTournament);
    }

    @Override
    public void startTournament(Tournament tournament) {
        List<String> participants = participants(tournament.getTournamentId());
        createMatch(participants, tournament);
    }

    @Override
    public List<String> participants(Long tournamentId) {
       List<TournamentRegisterTemp> participantsRegister = findAllByTournamentId(tournamentId);
       List<String> participants = new ArrayList<>();
       Set<Long> assignedUsers = new HashSet<>(); // Set pour éviter les doublons

// TODO refact Optional
       for (TournamentRegisterTemp registration : participantsRegister) {
           Optional<Long> userOpt = Optional.ofNullable(registration.getUserId());
           Optional<Long> teamOpt = Optional.ofNullable(registration.getTeamId());
           Optional<Long> clanOpt = Optional.ofNullable(registration.getClanId());

           if (userOpt.isPresent() && teamOpt.isEmpty() && clanOpt.isEmpty()) {
               User user1 = userService.getById(userOpt.get());
               if (!assignedUsers.contains(user1.getId())) {
                   User user2 = findAnotherAvailableUser(participantsRegister, assignedUsers);
                   if (user2 != null) {
                       participants.add(user1.getId() + "_" + user2.getId() + "_00_00");
                       assignedUsers.add(user1.getId());
                       assignedUsers.add(user2.getId());
                   }
               }

           } else if (teamOpt.isPresent() && clanOpt.isEmpty()) {
               Team team = teamService.getTeamById(String.valueOf(teamOpt.get()));
               List<User> teamUsers = team.getUsers();
               if (!assignedUsers.contains(teamUsers.get(0).getId()) && !assignedUsers.contains(teamUsers.get(1).getId())) {
                   participants.add(teamUsers.get(0).getId() + "_" + teamUsers.get(1).getId() + "_" + team.getTeamId() + "_00");
                   assignedUsers.add(teamUsers.get(0).getId());
                   assignedUsers.add(teamUsers.get(1).getId());
               }

           } else if (clanOpt.isPresent()) {
               Clan clan = clanService.getById(clanOpt.get());
               List<User> clanUsers = getTwoAvailableUsersFromClan(clan, assignedUsers);
               if (!clanUsers.isEmpty()) {
                   participants.add(clanUsers.get(0).getId() + "_" + clanUsers.get(1).getId() + "_00_" + clan.getClanId());
                   assignedUsers.add(clanUsers.get(0).getId());
                   assignedUsers.add(clanUsers.get(1).getId());
               }
           }
       }

        return null;
    }

    @Override
    public User findAnotherAvailableUser(List<TournamentRegisterTemp> participantsRegister, Set<Long> assignedUsers) {
        return participantsRegister.stream()
                .filter(registration -> registration.getTeamId() == null && registration.getClanId() == null)
                .map(TournamentRegisterTemp::getUserId)
                .filter(userId -> !assignedUsers.contains(userId)) // Comparer avec userId directement
                .map(userService::getById) // Obtenir l'objet utilisateur depuis l'ID
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getTwoAvailableUsersFromClan(Clan clan, Set<Long> assignedUsers) {
        // Filtre les membres du clan qui ne sont pas déjà assignés
        List<User> availableClanMembers = clan.getMembers().stream()
                .filter(user -> !assignedUsers.contains(user.getId()))
                .collect(Collectors.toList());

        if (availableClanMembers.size() >= 2) {
            // Retourne les deux premiers membres disponibles
            Collections.shuffle(availableClanMembers);
            return availableClanMembers.subList(0, 2);
        }

        return Collections.emptyList(); // Renvoie une liste vide s'il n'y a pas assez de membres disponibles
    }

    @Override
    public void createMatch(List<String> teams, Tournament tournament) {
        if (nbMatch.equals(1)) {
            winnerTournament(teams, tournament);
        } else {
            nbMatch = 0;
            for (int i = 0; i < teams.size(); i += 2) {
                nbMatch++;
                Match match = CreateMatchForm.toEntity(teams.get(i), teams.get(i + 1), tournament);
                matchService.save(match);
            }
        }
    }

    @Override
    public void nextTurn(String teamId, Tournament tournament) {
        nextTurnTeam.add(teamId);
        createMatch(nextTurnTeam, tournament);
    }

    @Override
    public void winnerTournament(List<String> winner, Tournament tournament) {
        List<String> winnerSplit = userService.getPlayersOfTeam(winner.getFirst());
        if (!Objects.equals(winnerSplit.get(2), "00")) {
            Long winnerEquip = userService.parsePlayerId(winnerSplit, 2);
            rankingService.winTournament(winnerEquip);
        } else if (!Objects.equals(winnerSplit.get(3), "00")) {
            Long winnerEquip = userService.parsePlayerId(winnerSplit, 3);
            rankingService.winTournament(winnerEquip);
        } else {
            Long userWinner1 = userService.parsePlayerId(winnerSplit, 0);
            rankingService.winTournament(userWinner1);
            Long userWinner2 = userService.parsePlayerId(winnerSplit, 1);
            rankingService.winTournament(userWinner2);
        }
        tournament.setWinner(winner.getFirst());
        tournamentRepository.save(tournament);
    }

    @Override
    public String getWinner(Long id) {
        Tournament tournament = getById(id);
        return tournament.getWinner();
    }

    @Override
    public List<Tournament> getTournamentsByDivisionAndType(Division division, TypeTournament typeTournament) {
        return tournamentRepository.findTournamentsByDivisionAndType(division, typeTournament);
    }
    //endregion

    //region Register Tournament
    // TODO verif du isCompleted
    @Override
    public TournamentRegisterTemp save(TournamentRegisterTemp tournamentRegisterTemp) {
        return tournamentRegisterTempRepository.save(tournamentRegisterTemp);
    }

    @Override
    public List<TournamentRegisterTemp> findAll() {
        return tournamentRegisterTempRepository.findAll();
    }

    @Override
    public TournamentRegisterTemp findById(Long id) {
        return tournamentRegisterTempRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cette inscription n'existe pas"));
    }

    @Override
    public void delete(TournamentRegisterTemp tournamentRegisterTemp) {
        tournamentRegisterTempRepository.delete(tournamentRegisterTemp);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(TournamentRegisterTemp tournamentRegisterTemp) {

    }

    @Override
    public List<TournamentRegisterTemp> findAllByTournamentId(Long tournamentId) {
        return tournamentRegisterTempRepository.findAllByTournamentId(tournamentId);
    }

    @Override
    public TournamentRegisterTemp inscriptionSolo(Long userId, TypeTournament typeTournament) {
        User user = userService.getById(userId);
        Division division = user.getRanking().getDivision();
        return matchMakingTournament(userId, null, null, division, typeTournament);
    }
    @Override
    public TournamentRegisterTemp inscriptionTeam(Long teamId, TypeTournament typeTournament) {
        String teamIdString = String.valueOf(teamId);
        Team team = teamService.getTeamById(teamIdString);
        Division division = team.getRanking().getDivision();
        return matchMakingTournament(null, teamId, null, division, typeTournament);
    }

    @Override
    public TournamentRegisterTemp inscriptionClan(Long clanId, TypeTournament typeTournament) {
        Clan clan = clanService.getById(clanId);
        Division division = clan.getRanking().getDivision();
        return matchMakingTournament(null, null, clanId, division, typeTournament);
    }
    // TODO Créer peut etre un DTO pour remplacer les surcharges ?
    @Override
    public TournamentRegisterTemp matchMakingTournament(Long userId, Long teamId, Long clanId, Division division, TypeTournament typeTournament) {
        List<Tournament> tournaments = getTournamentsByDivisionAndType(division, typeTournament);
        String nameDivision = division.name();

        TournamentRegisterTemp tournamentRegisterTemp = new TournamentRegisterTemp();

        if (tournaments.isEmpty()) {
            Tournament newTournament = createNewTournament(division, typeTournament);

            if (userId != null) {
                tournamentRegisterTemp.setUserId(userId);
                newTournament.setNbPlace(newTournament.getNbPlace() + 1);
            }
            if (teamId != null) {
                tournamentRegisterTemp.setTeamId(teamId);
                newTournament.setNbPlace(newTournament.getNbPlace() + 2);
            }
            if (clanId != null) {
                tournamentRegisterTemp.setClanId(clanId);
                newTournament.setNbPlace(newTournament.getNbPlace() + 2);
            }

            tournamentRegisterTemp.setTournamentId(newTournament.getTournamentId());
            tournamentRegisterTemp.setRegistrationType(nameDivision);
            return tournamentRegisterTempRepository.save(tournamentRegisterTemp);

        } else {
            Tournament tournament = tournaments.getFirst();

            if (userId != null) {
                tournamentRegisterTemp.setUserId(userId);
                tournament.setNbPlace(tournament.getNbPlace() + 1);
                checkIsCompleted(tournament);
            }
            if (teamId != null) {
                tournamentRegisterTemp.setTeamId(teamId);
                tournament.setNbPlace(tournament.getNbPlace() + 2);
                checkIsCompleted(tournament);
            }
            if (clanId != null) {
                tournamentRegisterTemp.setClanId(clanId);
                tournament.setNbPlace(tournament.getNbPlace() + 2);
                checkIsCompleted(tournament);
            }

            tournamentRegisterTemp.setTournamentId(tournament.getTournamentId());
            tournamentRegisterTemp.setRegistrationType(nameDivision);
            return tournamentRegisterTempRepository.save(tournamentRegisterTemp);
        }
    }

    @Override
    public void checkIsCompleted(Tournament tournament) {
        int typeTournamentOrdinal = tournament.getTypeTournament().ordinal();
        int nbPlaceTournament = tournament.getNbPlace();

        if (typeTournamentOrdinal == 0 && nbPlaceTournament == 8) {
            tournament.setCompleted(true);
            startTournament(tournament);
        } else if (typeTournamentOrdinal == 1 && nbPlaceTournament == 32) {
            tournament.setCompleted(true);
            startTournament(tournament);
        } else if (typeTournamentOrdinal == 2 && nbPlaceTournament == 64) {
            tournament.setCompleted(true);
            startTournament(tournament);
        } else {
            tournament.setCompleted(false);
        }

        update(tournament.getTournamentId(), tournament);
    }
    //endregion

}

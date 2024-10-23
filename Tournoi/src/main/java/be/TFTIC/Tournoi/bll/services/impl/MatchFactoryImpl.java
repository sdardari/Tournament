package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.*;
import be.TFTIC.Tournoi.bll.services.MatchFactory;
import be.TFTIC.Tournoi.dl.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MatchFactoryImpl implements MatchFactory {

    private final MatchService matchService;
    private final TournamentService tournamentService;
    private final TeamService teamService;
    private final ClanService clanService;
    private final UserService userService;
    private final RankingService rankingService;
    private Integer nbMatch = 0;
    private List<Team> nextTurnTeam = new ArrayList<>();

    @Override
    public void createMatch(List<Team> teams, Tournament tournament) {
//        if(nbMatch.equals(1)){
//            winnerTournament(teams, tournament);
//        }
//        if (nbMatch.equals(0) || nbMatch.equals(nextTurnTeam.size())){
//            nbMatch = 0;
//            for (int i = 0; i < teams.size(); i += 2) {
//                Match match = CreateMatchForm.toEntity(teams.get(i), teams.get(i+1), tournament);
//                matchService.save(match);
//                nbMatch++;
//            }
//        }
    }

    @Override
    public String determineMatchWinner(Match match) {
        int team1Wins = 0;
        int team2Wins = 0;

        if (determinerSetWinner(match.getScoreTeam1Set1(), match.getScoreTeam2Set1())
                .equals(match.getTeam1Players())) {
            team1Wins++;
        } else {
            team2Wins++;
        }

        if (determinerSetWinner(match.getScoreTeam1Set2(), match.getScoreTeam2Set2())
                .equals("Team 1")) {
            team1Wins++;
        } else {
            team2Wins++;
        }

        if (match.getScoreTeam1Set3() != null) {
            if (determinerSetWinner(match.getScoreTeam1Set3(), match.getScoreTeam2Set3())
                    .equals("Team 1")) {
                team1Wins++;
            } else {
                team2Wins++;
            }
        }
        String winner = team1Wins >= 2 ? match.getTeam1Players() : match.getTeam2Players();
        String loser = team1Wins >= 2 ? match.getTeam2Players() : match.getTeam1Players();
        if (match.getTournament() != null){
            tournamentService.nextTurn(winner, match.getTournament());
        }
        if(winner != null){
            List<String> usersWinnersId = userService.getPlayersOfTeam(winner);
            if(!Objects.equals(usersWinnersId.get(2), "00")){
                Team teamWinner = teamService.getTeamById(usersWinnersId.get(2));
                rankingService.winMatch(teamWinner.getRanking().getId());
            } else if (!Objects.equals(usersWinnersId.get(3), "00")) {
                Clan clanWinner = clanService.getById(userService.parsePlayerId(usersWinnersId, 3));
            }
            User userWinner1 = userService.getById(userService.parsePlayerId(usersWinnersId, 0));
            User userWinner2 = userService.getById(userService.parsePlayerId(usersWinnersId, 1));
            rankingService.winMatch(userWinner1.getRanking().getId());
            rankingService.winMatch(userWinner2.getRanking().getId());
        } else if (loser != null) {
            List<String> usersLosersId = userService.getPlayersOfTeam(loser);
            if(!Objects.equals(usersLosersId.get(2), "00")){
                Team teamWinner = teamService.getTeamById(usersLosersId.get(2));
                rankingService.lossMatch(teamWinner.getRanking().getId());
            } else if (!Objects.equals(usersLosersId.get(3), "00")) {
                Clan clanWinner = clanService.getById(userService.parsePlayerId(usersLosersId, 3));
            }
            User userLoser1 = userService.getById(userService.parsePlayerId(usersLosersId, 0));
            User userLoser2 = userService.getById(userService.parsePlayerId(usersLosersId, 1));
            rankingService.lossMatch(userLoser1.getRanking().getId());
            rankingService.lossMatch(userLoser2.getRanking().getId());
        }
        return winner;
    }

    private void winnerTournament(List<Team> teams, Tournament tournament) {
        for (Team team : teams) {
            System.out.println("Winner: " + team.getName());
        }
    }

    private String determinerSetWinner(int scoreTeam1, int scoreTeam2) {
        return scoreTeam1 > scoreTeam2 ? "Team 1" : "Team 2";
    }


}

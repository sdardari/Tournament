package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchFactory;
import be.TFTIC.Tournoi.bll.services.TournamentService;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchFactoryImpl implements MatchFactory {

    private final TournamentService tournamentService;

    @Override
    public String determineMatchWinner(Match match) {
        int team1Wins = 0;
        int team2Wins = 0;

        if (determinerSetWinner(match.getScoreTeam1Set1(), match.getScoreTeam2Set1()).equals("Team 1")) {
            team1Wins++;
        } else {
            team2Wins++;
        }

        if (determinerSetWinner(match.getScoreTeam1Set2(), match.getScoreTeam2Set2()).equals("Team 1")) {
            team1Wins++;
        } else {
            team2Wins++;
        }

        if (match.getScoreTeam1Set3() != null) {
            if (determinerSetWinner(match.getScoreTeam1Set3(), match.getScoreTeam2Set3()).equals("Team 1")) {
                team1Wins++;
            } else {
                team2Wins++;
            }
        }
        String winner = team1Wins >= 2 ? match.getTeam1Players() : match.getTeam2Players();
        if (match.getTournament() != null){
            tournamentService.nextTurn(winner, match.getTournament());
        }
        return winner;
    }

    private void winnerTournament(List<Team> teams, Tournament tournament) {
        // Logique pour déterminer le gagnant du tournoi
        for (Team team : teams) {
            System.out.println("Winner: " + team.getName());
        }
    }

    private String determinerSetWinner(int scoreTeam1, int scoreTeam2) {
        return scoreTeam1 > scoreTeam2 ? "Team 1" : "Team 2";
    }
}

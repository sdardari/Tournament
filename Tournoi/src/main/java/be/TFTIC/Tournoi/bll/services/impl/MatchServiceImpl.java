package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.dal.repositories.MatchRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

        private final MatchRepository matchRepository;
        private final UserRepository userRepository;

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le post avec cette id:" + id + "n'existe pas"));
    }

    /**
     * Création d'un match
     * @param match instance Match
     * @return enregistre dans la DB le match
     */
    public Match createMatch(Match match) {


        return matchRepository.save(match);
        }

    @Override
    public String creatTeam(Long user1, Long user2) {
        User player1 = getById(user1);
        User player2 = getById(user2);



        return "";
    }

    @Override
    public void update(Long id, Match match) {

    }

    @Override
    public void delete(Long id) {

    }

    /**
     * Méthode pour déterminer le gagnant du match
     * @param match
     * @return
     */
    public String determineMatchWinner(Match match) {
            int team1Wins = 0;
            int team2Wins = 0;

            if (determineSetWinner(match.getScoreTeam1Set1(), match.getScoreTeam2Set1()).equals("Team 1")) team1Wins++;
            if (determineSetWinner(match.getScoreTeam1Set2(), match.getScoreTeam2Set2()).equals("Team 1")) team1Wins++;
            if (match.getScoreTeam1Set3() != null && determineSetWinner(match.getScoreTeam1Set3(), match.getScoreTeam2Set3()).equals("Team 1")) team1Wins++;

            return team1Wins >= 2 ? "Team 1" : "Team 2";
        }

    @Override
    public String determinerSetWinner(int scoreTeam1, int scoreTeam2) {
        return "";
    }

    /**
     * Méthode pour déterminer le gagnant d'un set
     * @param scoreTeam1
     * @param scoreTeam2
     * @return
     */
        private String determineSetWinner(int scoreTeam1, int scoreTeam2) {
            return scoreTeam1 > scoreTeam2 ? "Team 1" : "Team 2";
        }
    }

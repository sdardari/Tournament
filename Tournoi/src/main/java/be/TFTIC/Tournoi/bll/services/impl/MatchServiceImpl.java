package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.dal.repositories.MatchRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Match createMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public void update(Long id, MatchForm matchForm) {
        Match oldMatch = getById(id);

        oldMatch.setTeam1Players(matchForm.teamId1());
        oldMatch.setTeam2Players(matchForm.teamId2());
        oldMatch.setPlace(matchForm.placeId());
        oldMatch.setScoreTeam1Set1(matchForm.scoreTeam1Set1());
        oldMatch.setScoreTeam2Set1(matchForm.scoreTeam2Set1());
        oldMatch.setScoreTeam1Set2(matchForm.scoreTeam1Set2());
        oldMatch.setScoreTeam2Set2(matchForm.scoreTeam2Set2());
        oldMatch.setScoreTeam1Set3(matchForm.scoreTeam1Set3());
        oldMatch.setScoreTeam2Set3(matchForm.scoreTeam2Set3());

        matchRepository.save(oldMatch);
    }

    @Override
    public void delete(Long id) {
        Match oldMatch = getById(id);
        matchRepository.delete(oldMatch);
    }

    @Override
    public String determineMatchWinner(Match match) {
        int team1Wins = 0;
        int team2Wins = 0;

        if (determinerSetWinner(match.getScoreTeam1Set1(), match.getScoreTeam2Set1()).equals("Team 1")) team1Wins++;
        if (determinerSetWinner(match.getScoreTeam1Set2(), match.getScoreTeam2Set2()).equals("Team 1")) team1Wins++;
        if (match.getScoreTeam1Set3() != null && determinerSetWinner(match.getScoreTeam1Set3(), match.getScoreTeam2Set3()).equals("Team 1"))
            team1Wins++;

        return team1Wins >= 2 ? "Team 1" : "Team 2";
    }

    @Override
    public String determinerSetWinner(int scoreTeam1, int scoreTeam2) {
        return scoreTeam1 > scoreTeam2 ? "Team 1" : "Team 2";
    }

    public  List<User> fromStringToUser(MatchForm matchForm) {
        List<User> users = new ArrayList<>();

        // Ici, pour votre bonne compréhension <3, je recupère dans un tableau les string entre "_".
        String[] team1 = matchForm.teamId1().split("_");
        String[] team2 = matchForm.teamId2().split("_");

        // Je reprend le string que est soit avant ou après le "_" et les convertir en Long ID.
        Long userId1 = Long.parseLong(team1[0]);
        Long userId2 = Long.parseLong(team2[1]);

        Long userId3 = Long.parseLong(team2[0]);
        Long userId4 = Long.parseLong(team1[1]);

        // Je les met dans une list.
        users.add(userRepository.findById(userId1).orElseThrow(() -> new RuntimeException("User 1 not found")));
        users.add(userRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User 2 not found")));
        users.add(userRepository.findById(userId3).orElseThrow(() -> new RuntimeException("User 3 not found")));
        users.add(userRepository.findById(userId4).orElseThrow(() -> new RuntimeException("User 4 not found")));

        return users;
    }
}

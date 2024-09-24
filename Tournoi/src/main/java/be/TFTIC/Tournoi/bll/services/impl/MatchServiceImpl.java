package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dal.repositories.MatchRepository;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final PlaceService placeService;
    private final UserService userService;

    @Override
    public List<Match> getAll() {
        return matchRepository.findAll();
    }

    @Override
    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le post avec cette id:" + id + "n'existe pas"));
    }

    @Override
    public Match createMatch(MatchForm matchForm, String team1, String team2) {
        Match match = matchForm.toEntity();
        match.setPlace(placeService.getPlaceById(matchForm.getPlaceId()));

        match.setTeam1Players(team1);
        match.setTeam2Players(team2);

        return matchRepository.save(match);
    }

    @Override
    public void update(Long id, MatchForm matchForm) {
        Match oldMatch = getById(id);
        List<User> users = userService.fromStringToUser(matchForm.toEntity());

        oldMatch.setTeam1Players(matchForm.getTeamId1());
        oldMatch.setTeam2Players(matchForm.getTeamId2());
        oldMatch.setPlace(placeService.getPlaceById(matchForm.getPlaceId()));
        oldMatch.setScoreTeam1Set1(matchForm.getScoreTeam1Set1());
        oldMatch.setScoreTeam2Set1(matchForm.getScoreTeam2Set1());
        oldMatch.setScoreTeam1Set2(matchForm.getScoreTeam2Set2());
        oldMatch.setScoreTeam2Set2(matchForm.getScoreTeam2Set2());
        oldMatch.setScoreTeam1Set3(matchForm.getScoreTeam1Set3());
        oldMatch.setScoreTeam2Set3(matchForm.getScoreTeam2Set3());

        matchRepository.save(oldMatch);
    }

    @Override
    public void delete(Long id) {
        Match oldMatch = getById(id);
        matchRepository.delete(oldMatch);
    }

    @Override
    public String determineMatchWinner(Match match) {
        //TO DO : renvoyer ID Winner
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

}

package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dal.repositories.MatchRepository;

import be.TFTIC.Tournoi.dl.entities.Match;
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

//    @Override
//    public Match createMatch(MatchForm matchForm, String team1, String team2) {
//        Match match = matchForm.toEntity();
//        match.setPlace(placeService.getPlaceById(matchForm.getPlaceId()));
//
//        match.setTeam1Players(team1);
//        match.setTeam2Players(team2);
//
//        return matchRepository.save(match);
//    }

    @Override
    public void update(Long id, MatchForm matchForm) {
        Match oldMatch = getById(id);

        oldMatch.setPlace(placeService.getPlaceById(matchForm.getPlaceId()));
        oldMatch.setScoreTeam1Set1(matchForm.getScoreTeam1Set1());
        oldMatch.setScoreTeam2Set1(matchForm.getScoreTeam2Set1());
        oldMatch.setScoreTeam1Set2(matchForm.getScoreTeam1Set2());
        oldMatch.setScoreTeam2Set2(matchForm.getScoreTeam2Set2());
        oldMatch.setScoreTeam1Set3(matchForm.getScoreTeam1Set3());
        oldMatch.setScoreTeam2Set3(matchForm.getScoreTeam2Set3());
        oldMatch.setDateOfMatch(matchForm.getDateOfMatch());
        oldMatch.setPlayed(true);

        matchRepository.save(oldMatch);
    }

    @Override
    public void delete(Long id) {
        Match oldMatch = getById(id);
        matchRepository.delete(oldMatch);
    }

    @Override
    public Match save(Match match){
        return matchRepository.save(match);
    }
}

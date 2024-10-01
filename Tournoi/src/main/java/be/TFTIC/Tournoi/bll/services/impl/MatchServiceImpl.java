package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.*;
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
                .orElseThrow(() -> new DoNotExistException("Le post avec cette id:" + id + "n'existe pas"));
    }

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

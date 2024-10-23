package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.RankingService;
import be.TFTIC.Tournoi.dal.repositories.RankingRepository;
import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.pl.models.rankingDTO.RankingDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    @Override
    public Ranking getRankingById(Long id) {
        return rankingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ranking not found"));
    }

    @Override
    public List<RankingDetailDTO> getAllRankings() {
        return rankingRepository.findAll().stream()
                .map(RankingDetailDTO::toEntity).collect(Collectors.toList());
    }

    @Override
    public void resetRanking(Long id) {
        Ranking rankingExisting = rankingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ranking not found"));
        initializer(id);
        rankingExisting.setInPromotionUp(false);
        rankingExisting.setInPromotionDown(false);
        rankingExisting.setDivision(Division.UNRANKED);
        rankingExisting.setNbMatches(0);
        rankingRepository.save(rankingExisting);
    }

    @Override
    public boolean isLegacyRanked(Long rankingId) {
        Ranking rankingLegacy = getRankingById(rankingId);
        return rankingLegacy.getNbMatches() >= 15;
    }

    @Override
    public void winMatch(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if (!ranking.isInPromotionUp()){
            ranking.setWins(ranking.getWins() + 1);
            ranking.setNbMatches(ranking.getNbMatches() + 1);
            ranking.setLeaguePoints(ranking.getLeaguePoints() + 20);
            isPromotedUp(rankingId);
        }else {
            statusPromotedUp(rankingId);
        }
    }

    @Override
    public void lossMatch(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if (!ranking.isInPromotionDown()){
            ranking.setLosses(ranking.getLosses() + 1);
            ranking.setNbMatches(ranking.getNbMatches() + 1);
            ranking.setLeaguePoints(ranking.getLeaguePoints() - 20);
            isPromotedDown(rankingId);
        }else {
            statusPromotedDown(rankingId);
        }
    }

    @Override
    public void winTournament(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(!ranking.isInPromotionUp()){
            ranking.setWinTournament(ranking.getWinTournament() + 1);
            ranking.setLeaguePoints(ranking.getLeaguePoints() + 40);
            isPromotedUp(rankingId);
        }else {
            promotedUp(rankingId);
            ranking.setWinTournament(ranking.getWinTournament() + 1);
            ranking.setLeaguePoints(ranking.getLeaguePoints() + 10);
            ranking.setNbMatches(ranking.getNbMatches() + 1);
        }
    }

    @Override
    public void isPromotedUp(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(ranking.getLeaguePoints() >= 100){
            ranking.setInPromotionUp(true);
            initializer(rankingId);
        }
    }

    @Override
    public void isPromotedDown(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(ranking.getLeaguePoints() >= 0){
           ranking.setInPromotionDown(true);
           initializer(rankingId);
        }
    }

    @Override
    public void statusPromotedUp(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if (ranking.getWins() == 3){
            promotedUp(rankingId);
        } else if (ranking.getLosses() == 3) {
            initializer(rankingId);
            ranking.setLeaguePoints(ranking.getLeaguePoints() + 90);
            ranking.setInPromotionUp(false);
        } else {
            ranking.setWins(ranking.getWins() + 1);
            ranking.setNbMatches(ranking.getNbMatches() + 1);
        }
    }

    @Override
    public void statusPromotedDown(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(ranking.getWins() >= 3){
            initializer(rankingId);
            ranking.setLeaguePoints(ranking.getLeaguePoints() + 35);
            ranking.setInPromotionDown(false);
        }else if (ranking.getLosses() >= 3) {
            promotedDown(rankingId);
        }else {
            ranking.setLosses(ranking.getLosses() + 1);
            ranking.setNbMatches(ranking.getNbMatches() + 1);
        }
    }

    @Override
    public void initializer(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        ranking.setLeaguePoints(0);
        ranking.setWins(0);
        ranking.setLosses(0);
    }

    @Override
    public void promotedUp(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(ranking.getDivision().ordinal() < Division.values().length - 1){
            ranking.setDivision(Division.values()[ranking.getDivision().ordinal() + 1]);
        }
        initializer(rankingId);
        ranking.setLeaguePoints(ranking.getLeaguePoints() + 35);
        ranking.setInPromotionUp(false);
    }

    @Override
    public void promotedDown(Long rankingId) {
        Ranking ranking = getRankingById(rankingId);
        if(ranking.getDivision().ordinal() > 0){
            ranking.setDivision(Division.values()[ranking.getDivision().ordinal() - 1]);
        }
        initializer(rankingId);
        ranking.setLeaguePoints(ranking.getLeaguePoints() + 90);
        ranking.setInPromotionDown(false);
    }
}

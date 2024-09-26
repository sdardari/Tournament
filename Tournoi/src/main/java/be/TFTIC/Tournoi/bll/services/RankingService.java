package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.pl.models.rankingDTO.RankingDetailDTO;

import java.util.List;

public interface RankingService {
    Ranking getRankingById(Long id);
    List<RankingDetailDTO> getAllRankings();
    void resetRanking(Long id);

    // region Bll for ranking
    boolean isLegacyRanked(Long rankingId);
    void winMatch(Long rankingId);
    void lossMatch(Long rankingId);
    void isPromotedUp(Long rankingId);
    void isPromotedDown(Long rankingId);
    void statusPromotedUp(Long rankingId);
    void statusPromotedDown(Long rankingId);
    void initializer(Long rankingId);
    void promotedUp(Long rankingId);
    void promotedDown(Long rankingId);
    // endregion
}
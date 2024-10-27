package be.TFTIC.Tournoi.pl.models.rankingDTO;

import be.TFTIC.Tournoi.dl.entities.Ranking;
import lombok.Getter;

public record RankingDetailDTO(
        Long id,
        int nbMatches,
        int leaguePoints,
        int wins,
        int losses,
        String division
) {
    public static RankingDetailDTO toEntity(Ranking ranking) {
        return new RankingDetailDTO(
                ranking.getId(),
                ranking.getNbMatches(),
                ranking.getLeaguePoints(),
                ranking.getWins(),
                ranking.getLosses(),
                ranking.getDivision().name()
        );
    }
}

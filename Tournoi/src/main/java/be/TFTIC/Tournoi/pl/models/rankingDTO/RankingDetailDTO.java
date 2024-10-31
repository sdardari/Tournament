package be.TFTIC.Tournoi.pl.models.rankingDTO;

import be.TFTIC.Tournoi.dl.entities.Ranking;
import lombok.Getter;

public record RankingDetailDTO(
        Long id,
        int nbMatches,
        int leaguePoints,
        int wins,
        int losses,
        int winTournament,
        String division
) {
    public static RankingDetailDTO fromEntity(Ranking ranking) {
        return new RankingDetailDTO(
                ranking.getId(),
                ranking.getNbMatches(),
                ranking.getLeaguePoints(),
                ranking.getWins(),
                ranking.getLosses(),
                ranking.getWinTournament(),
                ranking.getDivision().name()
        );
    }
}

package be.TFTIC.Tournoi.pl.models.rankingDTO;

import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.enums.Division;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RankingRegisterForm {
    private int nbMatches;
    private int leaguePoints;
    private int wins;
    private int losses;
    private Division division;

    public Ranking toEntity() {
        Ranking ranking = new Ranking();
        ranking.setNbMatches(nbMatches);
        ranking.setLeaguePoints(leaguePoints);
        ranking.setWins(wins);
        ranking.setLosses(losses);
        ranking.setDivision(division);
        return ranking;
    }

    public void updateEntity(Ranking ranking) {
        ranking.setNbMatches(nbMatches);
        ranking.setLeaguePoints(leaguePoints);
        ranking.setWins(wins);
        ranking.setLosses(losses);
        ranking.setDivision(division);
    }
}
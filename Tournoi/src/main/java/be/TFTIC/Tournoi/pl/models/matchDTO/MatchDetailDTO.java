package be.TFTIC.Tournoi.pl.models.matchDTO;


import be.TFTIC.Tournoi.dl.entities.Match;

import java.time.LocalDate;

public record MatchDetailDTO(
        Long matchId,
        String team1Players,
        String team2Players,
        String placeName, // Doit être un String
        String tournamentName, // Doit être un String
        Integer scoreTeam1Set1,
        Integer scoreTeam2Set1,
        Integer scoreTeam1Set2,
        Integer scoreTeam2Set2,
        Integer scoreTeam1Set3,
        Integer scoreTeam2Set3,
        LocalDate dateOfMatch
) {
    public static MatchDetailDTO fromMatch(Match match) {
        String placeName = match.getPlace() != null ? match.getPlace().getNameClub() : null;
        String tournamentName = match.getTournament() != null ? match.getTournament().getName() : null;
        return new MatchDetailDTO(
                match.getId(),
                match.getTeam1Players(),
                match.getTeam2Players(),
                placeName,  // Utilise le nom du lieu
                tournamentName,
                match.getScoreTeam1Set1(),
                match.getScoreTeam1Set2(),
                match.getScoreTeam1Set3(),
                match.getScoreTeam2Set1(),
                match.getScoreTeam2Set2(),
                match.getScoreTeam2Set3(),
                match.getDateOfMatch()
        );
    }
}
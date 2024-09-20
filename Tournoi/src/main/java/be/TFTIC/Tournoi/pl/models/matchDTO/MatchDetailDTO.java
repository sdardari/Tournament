//package be.TFTIC.Tournoi.pl.models.matchDTO;
//
//
//import be.TFTIC.Tournoi.dl.entities.Match;
//
//import java.time.LocalDate;
//
//public record MatchDetailDTO(
//        Long matchId,
//        String team1Players,
//        String team2Players,
//        String placeName,
//        String tournamentName,
//        Integer scoreTeam1Set1,
//        Integer scoreTeam2Set1,
//        Integer scoreTeam1Set2,
//        Integer scoreTeam2Set2,
//        Integer scoreTeam1Set3,
//        Integer scoreTeam2Set3,
//        LocalDate dateOfMatch
//) {
//    public static MatchDetailDTO fromMatch(Match match) {
//        return new MatchDetailDTO(
//                match.getMatchId(),
//                match.getTeam1Players(),
//                match.getTeam2Players(),
//                match.getPlace().getName(),
//                match.getTournament() != null ? match.getTournament().getName() : null,
//                match.getScoreTeam1Set1(),
//                match.getScoreTeam1Set2(),
//                match.getScoreTeam1Set3(),
//                match.getScoreTeam2Set1(),
//                match.getScoreTeam2Set2(),
//                match.getScoreTeam2Set3()
//
//        );
//    }
//}
package be.TFTIC.Tournoi.pl.models.matchDTO;
import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.dl.entities.Team;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class MatchForm {
    private Long placeId;
    private Integer scoreTeam1Set1;
    private Integer scoreTeam2Set1;
    private Integer scoreTeam1Set2;
    private Integer scoreTeam2Set2;
    private Integer scoreTeam1Set3;
    private Integer scoreTeam2Set3;
    private LocalDateTime dateOfMatch;

    // Getters and setters
    public Match toEntity() {

        Match match = new Match();
        match.setScoreTeam1Set1(this.scoreTeam1Set1);
        match.setScoreTeam2Set1(this.scoreTeam2Set1);
        match.setScoreTeam1Set2(this.scoreTeam1Set2);
        match.setScoreTeam2Set2(this.scoreTeam2Set2);
        match.setScoreTeam1Set3(this.scoreTeam1Set3);
        match.setScoreTeam2Set3(this.scoreTeam2Set3);
        match.setDateOfMatch(this.dateOfMatch);
        return match;
    }
}
package be.TFTIC.Tournoi.pl.models.matchDTO;


import be.TFTIC.Tournoi.dl.entities.Match;
import jakarta.validation.constraints.NotBlank;

import java.util.Locale;

public record MatchForm(
        @NotBlank
    String teamId1,
    @NotBlank
    String teamId2,
    @NotBlank
    Long placeId,
    @NotBlank
    Long tournamentId,
    Integer scoreTeam1Set1,
    Integer scoreTeam2Set1,
    Integer scoreTeam1Set2,
    Integer scoreTeam2Set2,
    Integer scoreTeam1Set3,
    Integer scoreTeam2Set3
) {
    public Match toEntity() {
        return new Match(teamId1,teamId2,placeId,scoreTeam1Set1,scoreTeam1Set2,scoreTeam1Set3,scoreTeam2Set1,scoreTeam2Set2,scoreTeam2Set3);
    }
}

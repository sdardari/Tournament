package be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TournamentRegisterTeamForm {
    private Long teamId;
    private Long tournamentId;

    public TournamentRegisterTeamForm toEntity() {
        TournamentRegisterTeamForm registration = new TournamentRegisterTeamForm();
        registration.setTeamId(this.teamId);
        registration.setTournamentId(this.tournamentId);
        return registration;
    }
}
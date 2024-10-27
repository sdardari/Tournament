package be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TournamentRegisterSoloForm {

    private Long userId1;
    private Long tournamentId;

    // Convert to entity
    public TournamentRegisterSoloForm toEntity() {
        TournamentRegisterSoloForm registration = new TournamentRegisterSoloForm();
        registration.setUserId1(this.userId1);
        registration.setTournamentId(this.tournamentId);
        return registration;
    }
}
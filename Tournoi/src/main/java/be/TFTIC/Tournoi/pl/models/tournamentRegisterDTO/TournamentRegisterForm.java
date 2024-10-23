package be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TournamentRegisterForm {

    private Long userId1;
    private Long userId2;
    private Long teamId;
    private Long clanId;
    private Long tournamentId;

    // Convert to entity
    public TournamentRegisterForm toEntity() {
        TournamentRegisterForm registration = new TournamentRegisterForm();
        registration.setUserId1(this.userId1);
        registration.setUserId2(this.userId2);
        registration.setTeamId(this.teamId);
        registration.setClanId(this.clanId);
        registration.setTournamentId(this.tournamentId);
        return registration;
    }
}
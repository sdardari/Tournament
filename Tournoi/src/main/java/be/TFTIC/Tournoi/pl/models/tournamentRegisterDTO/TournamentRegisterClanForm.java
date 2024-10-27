package be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TournamentRegisterClanForm {

    private Long clanId;
    private Long tournamentId;

    public TournamentRegisterClanForm toEntity() {
        TournamentRegisterClanForm registration = new TournamentRegisterClanForm();
        registration.setClanId(this.clanId);
        registration.setTournamentId(this.tournamentId);
        return registration;
    }
}
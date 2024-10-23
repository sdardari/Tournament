package be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO;

import be.TFTIC.Tournoi.dl.entities.*;

public record TournamentRegistrationDetailDTO(
        Long id,
        Long userId,
        Long teamId,
        Long clanId,
        Long tournamentId,
        String registrationType // Peut être "solo", "team", ou "clan"
) {

    public static TournamentRegistrationDetailDTO fromEntity(TournamentRegisterTemp registration) {
        return new TournamentRegistrationDetailDTO(
                registration.getId(),
                registration.getUserId(),   // Assure-toi que `getUser1()` retourne bien un User et non un Long
                registration.getTeamId(),
                registration.getClanId(),
                registration.getTournamentId(),
                registration.getRegistrationType()
        );
    }
}

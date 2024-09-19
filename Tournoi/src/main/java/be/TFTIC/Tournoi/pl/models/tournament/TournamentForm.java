package be.TFTIC.Tournoi.pl.models.tournament;

import be.TFTIC.Tournoi.dl.entities.Tournament;
import jakarta.validation.constraints.NotBlank;

public record TournamentForm(
        @NotBlank
        String name,
        String localisation,
        int nbPlace
) {
    public Tournament toEntity(){
        return new Tournament(name, localisation, nbPlace);
    }

}

package be.TFTIC.Tournoi.pl.models.tournament;

import be.TFTIC.Tournoi.dl.entities.Tournament;

public record TournamentDTO(

        String name,
        String Localisation,
        int nbPlace
        
) {

    public static TournamentDTO fromEntity(Tournament tournament){
        return new TournamentDTO(tournament.getName(), tournament.getLocation(), tournament.getNbPlace());
    }
}

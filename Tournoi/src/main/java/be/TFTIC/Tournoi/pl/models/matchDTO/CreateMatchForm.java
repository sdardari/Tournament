package be.TFTIC.Tournoi.pl.models.matchDTO;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;

public record CreateMatchForm (

){
    public static Match toEntity(String team1, String team2, Tournament tournament){
        return new Match(team1, team2, tournament);
    }
}

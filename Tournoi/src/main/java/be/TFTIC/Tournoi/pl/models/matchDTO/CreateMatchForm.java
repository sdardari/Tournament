package be.TFTIC.Tournoi.pl.models.matchDTO;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.Tournament;

public record CreateMatchForm (

){
    public static Match toEntity(Team team1, Team team2, Tournament tournament){
        return new Match(team1.getTeamId(), team2.getTeamId(), tournament);
    }
}

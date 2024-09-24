package be.TFTIC.Tournoi.pl.models.team;

import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;

import java.util.Arrays;

public record TeamFormUpdate(
    String name
) {
    public Team toEntity(){
        return new Team(name);
    }
}

package be.TFTIC.Tournoi.pl.models.team;

import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;

import java.lang.reflect.Array;
import java.util.Arrays;

public record TeamForm(
    String name
) {
    public Team toEntity(String teamId, User user1, User user2){
        return new Team(teamId, name, Arrays.asList(user1,user2));
    }
}

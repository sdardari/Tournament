package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;

import java.util.Set;

public record JoinClanDTO(
        Long id,
        String name,
        boolean isPrivate,
        int minimumTrophies,
        String president,
        Set<User> members,
        String message
        ){

    public static JoinClanDTO fromEntity(Clan clan, String message){
        return new JoinClanDTO(
                clan.getClanId(),
                clan.getName(),
                clan.getIsPrivate(),
                clan.getMinimumTrophies(),
                clan.getPresident(),
                clan.getMembers(),
                message
        );
    }
}
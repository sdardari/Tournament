package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;

import java.util.Set;

public record ClanDTO (
        Long id,
        String name,
        boolean isPrivate,
        int minimumTrophies,
        String president,
        Set<User> members,
        String message
        ){

    public static ClanDTO fromEntity(Clan clan, String message){
        return new ClanDTO(clan.getClanId(),clan.getName(),clan.getIsPrivate(),clan.getMinimumTrophies(),clan.getPresident(),clan.getMembers(),message);
    }
}
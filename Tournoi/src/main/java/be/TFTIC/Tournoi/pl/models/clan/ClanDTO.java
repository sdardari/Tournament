package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;

import java.util.Set;

public record ClanDTO (
        Long id,
        String name,
        boolean isPrivate,
        String president,
        Set<User> members
        ){

    public static ClanDTO fromEntity(Clan clan){
        return new ClanDTO(clan.getClanId(),clan.getName(),clan.getIsPrivate(),clan.getPresident(),clan.getMembers());
    }
}
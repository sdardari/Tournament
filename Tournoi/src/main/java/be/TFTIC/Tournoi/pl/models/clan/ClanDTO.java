package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.CLanRole;
import java.util.Map;
import java.util.Set;

public record ClanDTO (
        Long id,
        String name,
        boolean isPrivate,
        int minimumTrophies,
        Set<User> members
        ){

    public static ClanDTO fromEntity(Clan clan){
        return new ClanDTO(clan.getClanId(),clan.getName(),clan.getIsPrivate(),clan.getMinimumTrophies(),clan.getMembers());
    }
}
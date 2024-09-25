package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;

import java.util.List;
import java.util.Set;

public record ClanDTO (
        Long id,
        String name,
        boolean isPrivate,
        int minimumTrophies,
        String president,
        List<ClanMemberDTO> members,
        String message
        ){

    public static ClanDTO fromEntity(Clan clan, String message){
        List<ClanMemberDTO> clanMemberDTOS=clan.getMembers().stream()
                .map(ClanMemberDTO::fromEntity)
                .toList();
        return new ClanDTO(clan.getClanId(),
                clan.getName(),
                clan.getIsPrivate(),
                clan.getMinimumTrophies(),
                clan.getPresident(),
                clanMemberDTOS,
                message);
    }
}
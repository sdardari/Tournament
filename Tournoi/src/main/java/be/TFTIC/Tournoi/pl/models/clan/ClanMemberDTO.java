package be.TFTIC.Tournoi.pl.models.clan;

import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.User.UserParticipantDTO;

public record ClanMemberDTO (
    String username,
    String firstname,
    String lastname
    //int ranking
){
    public static ClanMemberDTO fromEntity(User user){
        return new ClanMemberDTO(user.getUsername(), user.getFirstname(), user.getLastname()/*,user.getRanking()*/);
    }
}

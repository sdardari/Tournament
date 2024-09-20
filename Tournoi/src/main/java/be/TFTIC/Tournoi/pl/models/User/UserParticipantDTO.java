package be.TFTIC.Tournoi.pl.models.User;

import be.TFTIC.Tournoi.dl.entities.User;

public record UserParticipantDTO (

        String username,
        String firstname,
        String lastname,
        int ranking

){

    public static UserParticipantDTO fromEntity(User user){
        return new UserParticipantDTO(user.getUsername(), user.getFirstname(), user.getLastname(),user.getRanking());
    }
}

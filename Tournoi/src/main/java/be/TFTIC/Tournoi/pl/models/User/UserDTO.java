package be.TFTIC.Tournoi.pl.models.User;

import be.TFTIC.Tournoi.dl.entities.User;

public record UserDTO (
        Long id,
        String username,
        String firstname,
        String lastname,
        String email
){

    public static UserDTO fromEntity(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());
    }
}

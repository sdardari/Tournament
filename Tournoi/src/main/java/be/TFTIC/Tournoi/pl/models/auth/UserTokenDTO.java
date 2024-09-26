package be.TFTIC.Tournoi.pl.models.auth;

import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;

public record UserTokenDTO
        (    Long id,
             String username,
             UserRole role,
             String token)
{
    public static UserTokenDTO fromEntity(User user, String token ){
        return new UserTokenDTO(user.getId(),user.getUsername(),user.getRole(),token);
    }
}


package be.TFTIC.Tournoi.pl.models.User;

import be.TFTIC.Tournoi.dl.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {

        private Long id;
    private String username;
    private  String firstname;
    private String lastname;
    private String email;


    public static UserDTO fromEntity(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname(), user.getEmail());
    }

}

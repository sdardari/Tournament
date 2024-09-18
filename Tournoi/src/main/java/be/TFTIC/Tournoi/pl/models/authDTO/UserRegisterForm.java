package be.TFTIC.Tournoi.pl.models.authDTO;


import be.TFTIC.Tournoi.dl.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterForm {

    @NotBlank
    @Size(max = 20)
    String username;
    @NotBlank
    String firstname;
    @NotBlank
    String lastname;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;

    //rajouter les validation ci-dessus
        public User toEntity () {
        return new User(username, firstname, lastname, email, password);

    }
}

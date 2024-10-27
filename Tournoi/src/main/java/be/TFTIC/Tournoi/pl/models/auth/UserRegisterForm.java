package be.TFTIC.Tournoi.pl.models.auth;


import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.il.validators.alreadyExist.AlreadyExist;
import be.TFTIC.Tournoi.il.validators.mustBeTheSame.MustBeTheSame;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@MustBeTheSame
public class UserRegisterForm {

    @NotBlank
    @Size(max = 20)
    @AlreadyExist(message = "This username is already in use")
    String username;
    @NotBlank
    String firstname;
    @NotBlank
    String lastname;
    @NotBlank
    @Email
    String email;
    @NotBlank
    @Size(min = 6)
    String password;

    String confirmPassword;

    //rajouter les validation ci-dessus
    public User toEntity () {
        return new User(username, firstname, lastname, email, password);
    }
}
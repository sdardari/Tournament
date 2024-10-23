package be.TFTIC.Tournoi.pl.models.User;

import lombok.*;

@Data
@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class UserForm {

    private String username;
    private String firstname;
    private String lastname;
    private String email;

}

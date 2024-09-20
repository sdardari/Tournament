package be.TFTIC.Tournoi.pl.models.clan;


import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class CLanForm {

    @NotBlank
    @Size(max= 20)
    private String name;
    @NotBlank
    private boolean isPrivate;
    @NotBlank
    private int minimumTrophies;

    private Long presidentId;
    // mettre l'id de le user qui créer le clan
    // rajout par la suite de changement de position de président max 2

    public Clan toEntity(){
        return new Clan(name,isPrivate,minimumTrophies);
    }
}

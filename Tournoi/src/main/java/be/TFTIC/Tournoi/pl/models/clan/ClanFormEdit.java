package be.TFTIC.Tournoi.pl.models.clan;


import be.TFTIC.Tournoi.dl.entities.Clan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor
@AllArgsConstructor
public class ClanFormEdit {

    @NotBlank
    @Size(max= 20)
    private String name;
    @NotBlank
    private boolean isPrivate;

    public Clan toEntity(){
        return new Clan(name,isPrivate);
    }
}

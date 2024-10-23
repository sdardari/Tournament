package be.TFTIC.Tournoi.pl.models.tournament;

import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoloForm {
    private Long userId;
    private TypeTournament typeTournament;
}
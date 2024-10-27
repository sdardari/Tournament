package be.TFTIC.Tournoi.pl.models.tournament;

import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class TeamForm {
    private Long teamId;
    private TypeTournament typeTournament;
}

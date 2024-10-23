package be.TFTIC.Tournoi.pl.models.tournament;

import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ClanForm {
    private Long clanId;
    private TypeTournament typeTournament;
}

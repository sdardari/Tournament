package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.dl.enums.Division;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@NoArgsConstructor
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ici nous récupérons le nombre de match joué pour évaluer son expérience global.
    // Celà pourra etre utiliser, par exemple, pour déterminer si il peut commencer du ranked.
    private int nbMatches;

    // Celà représente ton rang: ex Bronze, etc
    @Enumerated(EnumType.STRING)
    private Division division;

    // Ici on stock le nombre de "LeaguePoint" accumulé dans un Tiers/division
    private int leaguePoints;

    private int wins;

    private int losses;

    private int winTournament;

    private boolean inPromotionUp;

    private boolean inPromotionDown;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id", nullable = true)
    private Clan clan;

    @PrePersist
    protected void onCreate() {
        this.division = Division.UNRANKED;
        this.leaguePoints = 0;
        this.nbMatches = 0;
        this.wins = 0;
        this.losses = 0;
    }
}

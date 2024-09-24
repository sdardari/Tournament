package be.TFTIC.Tournoi.dl.entities;

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
    private int experiencePoints;

    private int level;

    // Ici on stock le nombre de "LeaguePoint" accumulé dans un Tiers/division
    private int leaguePoints;

    private int wins;

    private int losses;

    private boolean inPromotion;

    private int promotionWin;

    private int promotionLoss;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = true)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id", nullable = true)
    private Clan clan;

    public boolean isLegacyRanked() {
        return experiencePoints != 0 && experiencePoints >= 15;
    }


}

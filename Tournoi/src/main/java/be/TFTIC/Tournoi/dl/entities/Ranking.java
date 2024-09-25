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
    private int nbMatches;

    private int division;

    // Ici on stock le nombre de "LeaguePoint" accumulé dans un Tiers/division
    private int leaguePoints;

    private int wins;

    private int losses;

    private boolean inPromotion;

    private int winPromotion;

    private int lossPromotion;

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
        return nbMatches >= 15;
    }
    public void winMatch() {

        if (inPromotion == false){
             this.wins++;
             this.nbMatches++;
             this.leaguePoints += 20;

           checkIsPromoted();
        } else if (winPromotion == 2){

        }
    }
    public void lossMatch() {
        this.losses++;
        this.nbMatches++;
        this.leaguePoints -= 15;
        checkIsUnPromoted();
    }
    public void checkIsPromoted() {
        if (leaguePoints >=100){
            inPromotion = true;
        };
    }
    public boolean checkIsUnPromoted() {
        return leaguePoints <= 0;
    }

    public void promote(){
        division++;
        inPromotion = false;
        leaguePoints = 0;
        

    }


    // Constructeur personnalisé
    public Ranking(String playerName, Tier tier, int division) {
        this.playerName = playerName;
        this.tier = tier;
        this.division = division;
        this.division = 1;
        this.experiencePoints = 0;
        this.leaguePoints = 0;
        this.wins = 0;
        this.losses = 0;
        this.inPromotion = false;
        this.promotionWins = 0;
        this.promotionLosses = 0;
    }

    // Méthode pour perdre des LP
    public void loseMatch() {
        this.losses++;
        this.leaguePoints -= 15;  // Ex: Perd 15 LP par défaite
        if (this.leaguePoints < 0) {
            demote();
        }
    }

    // Vérifie si le joueur est en promotion
    private void checkPromotion() {
        if (this.leaguePoints >= 100) {
            this.inPromotion = true;
        }
    }

    // Promotion vers une nouvelle division
    public void winPromotionMatch() {
        this.promotionWins++;
        if (this.promotionWins == 2) {  // Ex: 2 victoires dans la promotion
            promote();
        }
    }

    // Méthode de promotion
    private void promote() {
        this.division++;
        this.leaguePoints = 0;
        this.inPromotion = false;
        this.promotionWins = 0;
        this.promotionLosses = 0;
    }

    // Méthode de rétrogradation
    private void demote() {
        this.division--;
        this.leaguePoints = 80;  // Ex: 80 LP après rétrogradation
    }
}

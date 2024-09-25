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

    public boolean isLegacyRanked() {
        return nbMatches >= 15;
    }
    public void winMatch() {
        if (!inPromotionUp){
             this.wins++;
             this.nbMatches++;
             this.leaguePoints += 20;
             checkIsPromoted();
        } else {
            statusPromotionUp();
        }
    }
    public void lossMatch() {
        if (!inPromotionDown){
            this.losses++;
            this.nbMatches++;
            this.leaguePoints -= 15;
            checkIsUnPromoted();
        }else{
            statusPromotionDown();
        }
    }
    public void checkIsPromoted() {
        if (leaguePoints >=100){
            inPromotionUp = true;
            initializer();
        };
    }
    public void checkIsUnPromoted() {
        if(leaguePoints >=0){
            inPromotionDown = true;
            initializer();
        };
    }
    public void statusPromotionUp() {
        if(wins == 3){
            promoteUp();
            inPromotionUp = false;
        } else if (losses == 3) {
            initializer();
            leaguePoints += 90;
            inPromotionUp = false;
        }
    }
    public void statusPromotionDown() {
        if(wins >= 3){
            initializer();
            leaguePoints += 35;
            inPromotionDown = false;
        }else if (losses >= 3) {
            promoteDown();
        }
    }
    public void initializer(){
        leaguePoints = 0;
        wins = 0;
        losses = 0;
    }
    public void promoteUp(){
        if (division.ordinal() < Division.values().length -1){
            division = Division.values()[division.ordinal() +1];// Passer à la division supérieure
        }
        initializer();
        leaguePoints += 35;
    }
    public void promoteDown(){
        if(division.ordinal() > 0){
            division = Division.values()[division.ordinal()-1];
        }
        initializer();
        leaguePoints += 90;
    }
}

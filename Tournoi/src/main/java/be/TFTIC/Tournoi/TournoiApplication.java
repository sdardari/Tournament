package be.TFTIC.Tournoi;

import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class TournoiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournoiApplication.class, args);
        // Créer un test simple pour vérifier le système de ranking
        testRankingSystem();
    }

    private static void testRankingSystem() {
        // Créer un objet Ranking
        Ranking ranking = new Ranking();
        ranking.setDivision(null);  // Pas de division avant 15 matchs
        ranking.setNbMatches(0);    // Commence à 0 match
        ranking.setLeaguePoints(0); // Commence à 0 LP

        // Simuler des matchs
        for (int i = 0; i < 15; i++) {
            ranking.winMatch();
            System.out.println("Match gagné. Points de ligue: " + ranking.getLeaguePoints() + " / Matchs joués: " + ranking.getNbMatches());
        }

        // Après 15 matchs, la personne peut être promue
        System.out.println("Après 15 matchs, éligible pour les rankings: " + ranking.isLegacyRanked());

        // Ajouter plus de victoires pour voir si la promotion fonctionne
        ranking.setLeaguePoints(90); // Début de promotion après quelques victoires
        ranking.winMatch();
        ranking.winMatch(); // Promotion réussie après 3 victoires
        System.out.println("Division après promotion: " + ranking.getDivision());
    }
}


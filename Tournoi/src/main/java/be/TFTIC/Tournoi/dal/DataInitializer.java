package be.TFTIC.Tournoi.dal;

import be.TFTIC.Tournoi.bll.services.TournamentRegisterService;
import be.TFTIC.Tournoi.bll.services.TournamentService;
import be.TFTIC.Tournoi.bll.services.impl.ClanServiceImpl;
import be.TFTIC.Tournoi.bll.services.impl.TeamServiceImpl;
import be.TFTIC.Tournoi.dal.repositories.*;
import be.TFTIC.Tournoi.dl.entities.*;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class DataInitializer {

  private final PasswordEncoder passwordEncoder;

  @Bean
  @Order(1)
  CommandLineRunner initUsers(UserRepository userRepository) {
    return args -> {
      Random random = new Random();

      String[] firstNames = {
        "Emma", "Louis", "Chloé", "Léo", "Jules", "Lina", "Lucas", "Jade", "Gabriel", "Manon",
        "Hugo", "Alice", "Nathan", "Zoé", "Arthur", "Mila", "Léa", "Paul", "Inès", "Tom",
        "Maël", "Rose", "Sacha", "Nina", "Maxime", "Eva", "Noé", "Anaïs", "Romain", "Sarah",
        "Antoine", "Julia", "Alexandre", "Camille", "Mathis", "Elisa", "Oscar", "Louise", "Raphaël", "Laura"
      };
      String[] lastNames = {
        "Martin", "Bernard", "Dubois", "Durand", "Moreau", "Simon", "Laurent", "Lefevre", "Roux", "David",
        "Garcia", "Moulin", "Lemoine", "Girard", "Renaud", "Garnier", "Mercier", "Petit", "Robert", "Richard",
        "Marchand", "Blanc", "Dupuis", "Perrin", "Gaillard", "Fournier", "Morin", "Gauthier", "Adam", "Bourgeois",
        "Rodriguez", "Lopez", "Muller", "Fontaine", "Chevalier", "Barbier", "Dumont", "Gonzalez", "Brunet", "Meyer"
      };

      for (int i = 1; i <= 35; i++) {
        User user = new User();

        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String username = (firstName.charAt(0) + lastName).toLowerCase() + i;

        user.setUsername(username);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setEmail(username + "@test.com");

        Ranking ranking = createRandomRanking(random);
        user.setRanking(ranking);
        user.setPassword(passwordEncoder.encode("password" + i));

        userRepository.save(user);
      }
    };
  }

  private Ranking createRandomRanking(Random random) {
    Division[] divisions = Division.values();
    Division division = divisions[random.nextInt(divisions.length)];

    Ranking ranking = new Ranking();
    ranking.setDivision(division);
    ranking.setNbMatches(getNbMatchesByDivision(division, random));
    ranking.setWins(getWinsByDivision(ranking.getNbMatches(), division, random));
    ranking.setLosses(ranking.getNbMatches() - ranking.getWins());
    ranking.setLeaguePoints(getLeaguePointsByDivision(division, random));
    ranking.setWinTournament(getWinTournamentByDivision(division, random));

    return ranking;
  }

  private int getNbMatchesByDivision(Division division, Random random) {
    switch (division) {
      case UNRANKED:
        return random.nextInt(10) + 1;
      case IRON:
      case BRONZE:
        return random.nextInt(50) + 10;
      case SILVER:
        return random.nextInt(100) + 50;
      case GOLD:
        return random.nextInt(200) + 100;
      case PLATINUM:
      case DIAMOND:
        return random.nextInt(300) + 200;
      case MASTER:
        return random.nextInt(500) + 300;
      case GRANDMASTER:
        return random.nextInt(700) + 500;
      case CHALLENGER:
        return random.nextInt(1000) + 800;
      default:
        return 0;
    }
  }

  private int getWinsByDivision(int nbMatches, Division division, Random random) {
    double winRate;
    switch (division) {
      case UNRANKED:
        winRate = 0.3;
        break;
      case IRON:
      case BRONZE:
        winRate = 0.4;
        break;
      case SILVER:
        winRate = 0.5;
        break;
      case GOLD:
        winRate = 0.55;
        break;
      case PLATINUM:
        winRate = 0.6;
        break;
      case DIAMOND:
        winRate = 0.65;
        break;
      case MASTER:
        winRate = 0.7;
        break;
      case GRANDMASTER:
        winRate = 0.75;
        break;
      case CHALLENGER:
        winRate = 0.8;
        break;
      default:
        winRate = 0.0;
    }
    return (int) (nbMatches * winRate) + random.nextInt(5);
  }

  private int getLeaguePointsByDivision(Division division, Random random) {
    switch (division) {
      case UNRANKED:
        return 0;
      case IRON:
        return random.nextInt(100);
      case BRONZE:
        return random.nextInt(200) + 100;
      case SILVER:
        return random.nextInt(300) + 200;
      case GOLD:
        return random.nextInt(400) + 300;
      case PLATINUM:
        return random.nextInt(500) + 400;
      case DIAMOND:
        return random.nextInt(600) + 500;
      case MASTER:
        return random.nextInt(700) + 600;
      case GRANDMASTER:
        return random.nextInt(800) + 700;
      case CHALLENGER:
        return random.nextInt(1000) + 800;
      default:
        return 0;
    }
  }

  private int getWinTournamentByDivision(Division division, Random random) {
    switch (division) {
      case UNRANKED:
        return 0;
      case IRON:
      case BRONZE:
        return random.nextInt(2);
      case SILVER:
        return random.nextInt(5);
      case GOLD:
        return random.nextInt(10);
      case PLATINUM:
        return random.nextInt(15);
      case DIAMOND:
        return random.nextInt(20);
      case MASTER:
        return random.nextInt(25);
      case GRANDMASTER:
        return random.nextInt(30);
      case CHALLENGER:
        return random.nextInt(40);
      default:
        return 0;
    }
  }

  @Bean
  @Order(2)
  CommandLineRunner initAddresses(AddressRepository addressRepository) {
    return args -> {
      if (addressRepository.count() == 0) {
        addressRepository.save(new Address(null, "Rue de la Loi", "16", "1000", "Bruxelles", "Belgique"));
        addressRepository.save(new Address(null, "Avenue Louise", "326", "1050", "Ixelles", "Belgique"));
        addressRepository.save(new Address(null, "Boulevard de la Sauvenière", "150", "4000", "Liège", "Belgique"));
        addressRepository.save(new Address(null, "Place Saint-Lambert", "1", "4000", "Liège", "Belgique"));
        addressRepository.save(new Address(null, "Rue Neuve", "50", "1000", "Bruxelles", "Belgique"));
        addressRepository.save(new Address(null, "Chaussée de Charleroi", "95", "1060", "Saint-Gilles", "Belgique"));
        addressRepository.save(new Address(null, "Rue du Miroir", "7", "5000", "Namur", "Belgique"));
        addressRepository.save(new Address(null, "Place de la Digue", "14", "6000", "Charleroi", "Belgique"));
        addressRepository.save(new Address(null, "Place Verte", "10", "7000", "Mons", "Belgique"));
        addressRepository.save(new Address(null, "Korenmarkt", "5", "9000", "Gand", "Belgique"));
        addressRepository.save(new Address(null, "Meir", "75", "2000", "Anvers", "Belgique"));
        addressRepository.save(new Address(null, "Grote Markt", "1", "8500", "Courtrai", "Belgique"));
        addressRepository.save(new Address(null, "Rue de Nimy", "25", "7000", "Mons", "Belgique"));
        addressRepository.save(new Address(null, "Chaussée de Waterloo", "115", "1180", "Uccle", "Belgique"));
        addressRepository.save(new Address(null, "Avenue de Tervuren", "250", "1150", "Woluwe-Saint-Pierre", "Belgique"));
        addressRepository.save(new Address(null, "Chaussée de Louvain", "412", "1200", "Woluwe-Saint-Lambert", "Belgique"));
        addressRepository.save(new Address(null, "Place de la République Française", "3", "5000", "Namur", "Belgique"));
        addressRepository.save(new Address(null, "Chaussée de Gand", "75", "1080", "Molenbeek-Saint-Jean", "Belgique"));
        addressRepository.save(new Address(null, "Place du Luxembourg", "8", "1050", "Ixelles", "Belgique"));
        addressRepository.save(new Address(null, "Rue de la Montagne", "10", "1000", "Bruxelles", "Belgique"));
        addressRepository.save(new Address(null, "Avenue des Champs-Élysées", "12", "75008", "Paris", "France"));
        addressRepository.save(new Address(null, "Oxford Street", "45", "W1D", "London", "United Kingdom"));
        addressRepository.save(new Address(null, "Friedrichstrasse", "101", "10117", "Berlin", "Germany"));
        addressRepository.save(new Address(null, "Piazza del Popolo", "3", "00187", "Rome", "Italy"));
        addressRepository.save(new Address(null, "Gran Vía", "15", "28013", "Madrid", "Spain"));
        addressRepository.save(new Address(null, "Nevsky Prospekt", "20", "191186", "Saint Petersburg", "Russia"));
        addressRepository.save(new Address(null, "Fifth Avenue", "350", "10018", "New York", "USA"));
        addressRepository.save(new Address(null, "Avenida Paulista", "1000", "01310-100", "São Paulo", "Brazil"));
        addressRepository.save(new Address(null, "Yonge Street", "250", "M5B 1R8", "Toronto", "Canada"));
        addressRepository.save(new Address(null, "Nanjing Road", "20", "200001", "Shanghai", "China"));
        addressRepository.save(new Address(null, "George Street", "56", "2000", "Sydney", "Australia"));
      }
    };
  }

  @Bean
  @Order(3)
  CommandLineRunner initPlaces(PlaceRepository placeRepository, AddressRepository addressRepository) {
    return args -> {
      if (placeRepository.count() == 0) {
        List<Address> addresses = addressRepository.findAll();

        if (!addresses.isEmpty()) {
          List<Place> places = new ArrayList<>();
          for (Address address : addresses) {
            Place place = new Place();
            String clubName = "Padel Club " + address.getCity();
            place.setNameClub(clubName);
            place.setAddress(address);
            places.add(place);
          }
          placeRepository.saveAll(places);
        }
      }
    };
  }

  // @Order(4)
  // CommandLineRunner initClansAndTeams(UserRepository userRepository, ClanRepository clanRepository, ClanServiceImpl clanService, RankingRepository rankingRepository) {
  //     return args -> {
  //         if (clanRepository.count() == 0 && userRepository.count() != 0) {
  //             Random random = new Random();
  //             List<User> allUsers = userRepository.findAll();
  //
  //             List<String> clanNames = List.of("FC Lionheart", "Silver Knights", "Iron Legion", "Golden Eagles", "Challenger Corps");
  //             List<String> teamNames = List.of("Blue Bulls", "Red Falcons", "Black Stallions", "Golden Phoenix", "Silver Arrows");
  //
  //             for (int i = 0; i < clanNames.size(); i++) {
  //                 Clan clan = new Clan();
  //                 clan.setName(clanNames.get(i));
  //                 clan.getMembers().add(allUsers.get(i));
  //                 clan.setPrivate(i % 2 == 0);
  //                 clan.setPresident("Président de " + clan.getName());
  //
  //                 Ranking ranking = createRandomRanking(random);
  //                 clan.setRanking(ranking);
  //
  //                 User user = allUsers.get(i);
  //                 clanService.addMemberToClanWithoutCheck(clan, user, ClanRole.MEMBER);
  //             }
  //         }
  //     };
  // }

  // @Order(5)
  // CommandLineRunner initTournaments(TournamentService tournamentService, TournamentRegisterService tournamentRegisterService, UserRepository userRepository, TeamRepository teamRepository, ClanRepository clanRepository) {
  //     return args -> {
  //         if (tournamentService.getAll().isEmpty()) {
  //             for (Division division : Division.values()) {
  //                 for (TypeTournament typeTournament : TypeTournament.values()) {
  //                     Tournament tournament = tournamentService.createNewTournament(division, typeTournament);
  //                     List<User> users = userRepository.findAll().subList(0, Math.min(10, userRepository.findAll().size()));
  //                     List<Team> teams = teamRepository.findAll().subList(0, Math.min(5, teamRepository.findAll().size()));
  //                     List<Clan> clans = clanRepository.findAll().subList(0, Math.min(5, clanRepository.findAll().size()));
  //
  //                     users.forEach(user -> tournamentRegisterService.inscriptionSolo(user.getId(), typeTournament));
  //                     teams.forEach(team -> tournamentRegisterService.inscriptionTeam(team.getTeamId()));
  //                     clans.forEach(clan -> tournamentRegisterService.inscriptionClan(clan.getClanId()));
  //
  //                     tournamentService.checkIsCompleted(tournament);
  //                 }
  //             }
  //         }
  //     };
  // }
}

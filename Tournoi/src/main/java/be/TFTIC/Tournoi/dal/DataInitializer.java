package be.TFTIC.Tournoi.dal;


import be.TFTIC.Tournoi.dal.repositories.AddressRepository;
import be.TFTIC.Tournoi.dal.repositories.ClanRepository;
import be.TFTIC.Tournoi.dal.repositories.PlaceRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Address;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@RequiredArgsConstructor
@Configuration
public class DataInitializer {


    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
      return args -> {
        if (userRepository.count() == 0) { // To avoid duplicate entries
          userRepository.save(new User(null, "adminUser", "Admin", "User", "admin@example.com", 0, passwordEncoder.encode("admin123"), UserRole.ADMIN, new ArrayList<>()));
          userRepository.save(new User(null, "johnDoe", "John", "Doe", "john.doe@example.com", 0, passwordEncoder.encode("password1"), UserRole.USER, new ArrayList<>()));
          userRepository.save(new User(null, "janeDoe", "Jane", "Doe", "jane.doe@example.com", 0, passwordEncoder.encode("password2"), UserRole.USER, new ArrayList<>()));
          userRepository.save(new User(null, "samSmith", "Sam", "Smith", "sam.smith@example.com", 0, passwordEncoder.encode("password3"), UserRole.USER, new ArrayList<>()));
          userRepository.save(new User(null, "lisaJones", "Lisa", "Jones", "lisa.jones@example.com", 0, passwordEncoder.encode("password4"), UserRole.USER, new ArrayList<>()));
          userRepository.save(new User(null, "mikeBrown", "Mike", "Brown", "mike.brown@example.com", 0, passwordEncoder.encode("password5"), UserRole.USER, new ArrayList<>()));
          userRepository.save(new User(null, "lucyDavis", "Lucy", "Davis", "lucy.davis@example.com", 0, passwordEncoder.encode("password6"), UserRole.USER, new ArrayList<>()));
        }
      };
    }

    @Bean
    CommandLineRunner initClans(ClanRepository clanRepository) {
      return args -> {
        // Vérifie si les clans n'ont pas déjà été insérés
        if (clanRepository.count() == 0) {
          clanRepository.save(new Clan("Warriors", false, 1000));
          clanRepository.save(new Clan("Shadow Hunters", true, 2000));
          clanRepository.save(new Clan("Dragon Slayers", false, 1500));
          clanRepository.save(new Clan("Mystic Knights", true, 500));
        }
      };
    }
    @Bean
    CommandLineRunner initPlaces(PlaceRepository placeRepository, AddressRepository addressRepository) {
        return args -> {
            if (placeRepository.count() == 0) { // Vérifie que les lieux n'ont pas déjà été insérés
                // Récupération des adresses existantes
                Address address1 = addressRepository.findById(1L).orElse(null);
                Address address2 = addressRepository.findById(2L).orElse(null);
                Address address3 = addressRepository.findById(3L).orElse(null);
                Address address4 = addressRepository.findById(4L).orElse(null);
                Address address5 = addressRepository.findById(5L).orElse(null);

                // Création des lieux
                Place place1 = new Place();
                place1.setNameClub("Royal Padel Club");
                place1.setAddress(address1);

                Place place2 = new Place();
                place2.setNameClub("Tennis Club de Paris");
                place2.setAddress(address2);

                Place place3 = new Place();
                place3.setNameClub("London Sports Academy");
                place3.setAddress(address3);

                Place place4 = new Place();
                place4.setNameClub("Berlin Padel Arena");
                place4.setAddress(address4);

                Place place5 = new Place();
                place5.setNameClub("Roma Padel Center");
                place5.setAddress(address5);

                // Sauvegarde dans la base de données
                placeRepository.save(place1);
                placeRepository.save(place2);
                placeRepository.save(place3);
                placeRepository.save(place4);
                placeRepository.save(place5);
            }
        };
    }

}

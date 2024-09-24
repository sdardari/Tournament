package be.TFTIC.Tournoi.dal;


import be.TFTIC.Tournoi.dal.repositories.AddressRepository;
import be.TFTIC.Tournoi.dal.repositories.PlaceRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Address;
import be.TFTIC.Tournoi.dl.entities.Place;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Configuration
public class DataInitializer {


    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) { // To avoid duplicate entries
                userRepository.save(new User(null, "adminUser", "Admin", "User", "admin@example.com", 0, passwordEncoder.encode("admin123"), UserRole.ADMIN));
                userRepository.save(new User(null, "johnDoe", "John", "Doe", "john.doe@example.com", 0, passwordEncoder.encode("password1"), UserRole.USER));
                userRepository.save(new User(null, "janeDoe", "Jane", "Doe", "jane.doe@example.com", 0, passwordEncoder.encode("password2"), UserRole.USER));
                userRepository.save(new User(null, "samSmith", "Sam", "Smith", "sam.smith@example.com", 0, passwordEncoder.encode("password3"), UserRole.USER));
                userRepository.save(new User(null, "lisaJones", "Lisa", "Jones", "lisa.jones@example.com", 0, passwordEncoder.encode("password4"), UserRole.USER));
                userRepository.save(new User(null, "mikeBrown", "Mike", "Brown", "mike.brown@example.com", 0, passwordEncoder.encode("password5"), UserRole.USER));
                userRepository.save(new User(null, "lucyDavis", "Lucy", "Davis", "lucy.davis@example.com", 0, passwordEncoder.encode("password6"), UserRole.USER));
            }
        };
    }

    @Bean
    CommandLineRunner initAddresses(AddressRepository addressRepository) {
        return args -> {
            if (addressRepository.count() == 0) { // Vérifie que les adresses n'ont pas déjà été insérées
                addressRepository.save(new Address(null, "Main Street", "123", "1000", "Bruxelles", "Belgique"));
                addressRepository.save(new Address(null, "Avenue des Champs-Élysées", "12", "75008", "Paris", "France"));
                addressRepository.save(new Address(null, "Oxford Street", "45", "W1D", "London", "United Kingdom"));
                addressRepository.save(new Address(null, "Friedrichstrasse", "101", "10117", "Berlin", "Germany"));
                addressRepository.save(new Address(null, "Piazza del Popolo", "3", "00187", "Rome", "Italy"));
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

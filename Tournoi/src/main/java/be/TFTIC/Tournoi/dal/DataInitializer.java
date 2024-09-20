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
            if (userRepository.count() == 0) { // Pour éviter les entrées en double
                userRepository.save(new User(null, "adminUser", "Admin", "User", "admin@example.com", null, passwordEncoder.encode("admin123"), UserRole.ADMIN, null));
                userRepository.save(new User(null, "johnDoe", "John", "Doe", "john.doe@example.com", null, passwordEncoder.encode("password1"), UserRole.USER, null));
                userRepository.save(new User(null, "janeDoe", "Jane", "Doe", "jane.doe@example.com", null, passwordEncoder.encode("password2"), UserRole.USER, null));
                userRepository.save(new User(null, "samSmith", "Sam", "Smith", "sam.smith@example.com", null, passwordEncoder.encode("password3"), UserRole.USER, null));
                userRepository.save(new User(null, "lisaJones", "Lisa", "Jones", "lisa.jones@example.com", null, passwordEncoder.encode("password4"), UserRole.USER, null));
                userRepository.save(new User(null, "mikeBrown", "Mike", "Brown", "mike.brown@example.com", null, passwordEncoder.encode("password5"), UserRole.USER, null));
                userRepository.save(new User(null, "lucyDavis", "Lucy", "Davis", "lucy.davis@example.com", null, passwordEncoder.encode("password6"), UserRole.USER, null));
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
            if (placeRepository.count() == 0) {
                // Récupérer les adresses persistées depuis la base de données
                Address address1 = addressRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Address 1 not found"));
                Address address2 = addressRepository.findById(2L).orElseThrow(() -> new IllegalArgumentException("Address 2 not found"));
                Address address3 = addressRepository.findById(3L).orElseThrow(() -> new IllegalArgumentException("Address 3 not found"));
                Address address4 = addressRepository.findById(4L).orElseThrow(() -> new IllegalArgumentException("Address 4 not found"));
                Address address5 = addressRepository.findById(5L).orElseThrow(() -> new IllegalArgumentException("Address 5 not found"));

                // Créer les places en utilisant les adresses persistées
                placeRepository.save(new Place("Padel Club Brussels", address1));
                placeRepository.save(new Place("Champs-Élysées Padel", address2));
                placeRepository.save(new Place("London Padel Center", address3));
                placeRepository.save(new Place("Berlin Padel Arena", address4));
                placeRepository.save(new Place("Rome Padel Stadium", address5));

            }
        };
    }
}

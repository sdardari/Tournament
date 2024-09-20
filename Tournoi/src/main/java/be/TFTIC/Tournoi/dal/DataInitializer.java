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

}

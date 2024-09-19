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
            if (addressRepository.count() == 0) {
                addressRepository.save(new Address("Rue de la Paix", "10", "1000", "Bruxelles", "Belgique"));
                addressRepository.save(new Address("Avenue Louise", "20", "1050", "Ixelles", "Belgique"));
                addressRepository.save(new Address("Rue Royale", "50", "1210", "Saint-Josse", "Belgique"));
                addressRepository.save(new Address("Boulevard de Waterloo", "35", "1000", "Bruxelles", "Belgique"));
                addressRepository.save(new Address("Chaussée de Charleroi", "12", "1060", "Saint-Gilles", "Belgique"));
            }
        };
    }

    @Bean
    CommandLineRunner initPlaces(PlaceRepository placeRepository, AddressRepository addressRepository) {
        return args -> {
            if (placeRepository.count() == 0) {
                // Récupération des adresses pour les associer aux places
                Address address1 = addressRepository.findById(1).orElse(null);
                Address address2 = addressRepository.findById(2).orElse(null);
                Address address3 = addressRepository.findById(3).orElse(null);

                if (address1 != null && address2 != null && address3 != null) {
                    placeRepository.save(new Place(null, "Padel Club Event Ottignies", address1));
                    placeRepository.save(new Place(null, "Casa Del Padel", address2));
                    placeRepository.save(new Place(null, "Padel Center Ixelles", address3));
                }
            }
        };
    }
}

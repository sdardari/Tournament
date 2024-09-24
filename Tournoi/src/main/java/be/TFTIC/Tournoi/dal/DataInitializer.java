package be.TFTIC.Tournoi.dal;


import be.TFTIC.Tournoi.dal.repositories.UserRepository;
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
}

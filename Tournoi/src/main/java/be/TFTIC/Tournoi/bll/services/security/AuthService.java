package be.TFTIC.Tournoi.bll.services.security;

import be.TFTIC.Tournoi.dl.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    User register(User user);
    User login (String username, String password);
}

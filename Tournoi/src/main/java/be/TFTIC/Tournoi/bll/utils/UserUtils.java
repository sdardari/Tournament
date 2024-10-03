package be.TFTIC.Tournoi.bll.utils;

import be.TFTIC.Tournoi.dl.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static User getAuthentification (){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
//TODO a vérifier, et appliquer en remplacant les authentification
}
//ok
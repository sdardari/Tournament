package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.User.UserForm;
import be.TFTIC.Tournoi.pl.models.auth.UserRegisterForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface UserService {


    UserDTO createUser(UserRegisterForm userForm);

    User getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UserRegisterForm userForm);

    void deleteUser(Long id);

    UserRole getUserRole(Long id);

    List<UserDTO> findByCriteria(UserForm form);

    // region Bll extract User from String
    List<User> fromStringToUser(Match match);
    int getSize(String team);
    User addUserFromMatchForm(String team, int id);
    Long parsePlayerId(List<String> teams, int id);
    List<String> getPlayersOfTeam(String teamId);
    User getById(Long id);

    // endregion





    boolean existsByUsername(String username);

}

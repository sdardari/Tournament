package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.User.UserForm;
import be.TFTIC.Tournoi.pl.models.auth.UserRegisterForm;

import java.util.List;

public interface UserService {


    UserDTO createUser(UserRegisterForm userForm);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserRegisterForm userForm);
    void deleteUser(Long id);
    UserRole getUserRole (Long id);
    List<UserDTO> findByCriteria(UserForm form);


    List<User> fromStringToUser(Match match);










}

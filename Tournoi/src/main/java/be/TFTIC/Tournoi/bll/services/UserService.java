package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.user.UserDTO;
import be.TFTIC.Tournoi.pl.models.user.UserForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserRegisterForm;

import java.util.List;

public interface UserService {


    UserDTO createUser(UserRegisterForm userForm);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserRegisterForm userForm);
    void deleteUser(Long id);
    UserRole getUserRole (Long id);
    List<UserDTO> findByCriteria(UserForm form);










}
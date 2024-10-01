package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.service.UserService;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.User.UserForm;
import be.TFTIC.Tournoi.pl.models.auth.UserRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Obtenir un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = UserDTO.fromEntity(userService.getUserById(id));
        return ResponseEntity.ok(userDTO);
    }

    // Obtenir la liste de tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Mettre à jour les informations d'un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserRegisterForm userForm) {
        UserDTO updatedUser = userService.updateUser(id, userForm);
        return ResponseEntity.ok(updatedUser);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Rechercher des utilisateurs par critères (utilisant Specifications)
    @PostMapping("/search")
    public ResponseEntity<List<UserDTO>> findByCriteria(@RequestBody UserForm form) {
        List<UserDTO> users = userService.findByCriteria(form);
        return ResponseEntity.ok(users);
    }

    // Obtenir le rôle d'un utilisateur par ID
    @GetMapping("/{id}/role")
    public ResponseEntity<UserRole> getUserRole(@PathVariable Long id) {
        UserRole role = userService.getUserRole(id);
        return ResponseEntity.ok(role);
    }
}
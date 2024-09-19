package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.bll.specifications.UserSpecification;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.User.UserForm;
import be.TFTIC.Tournoi.pl.models.authDTO.UserRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDTO createUser(UserRegisterForm userForm) {
        User user = userForm.toEntity();
        userRepository.save(userForm.toEntity());
        return UserDTO.fromEntity(user);
        //        return UserDTO.fromEntity(repo.save(form.toEntity()));
    }


    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Username with this id does not exist"));
        return UserDTO.fromEntity(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserRegisterForm userForm) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " doesn't exist."));
        user.setUsername(userForm.getUsername());
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        return UserDTO.fromEntity(userRepository.save(user));

    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Username with id " + id + " doesn't exist."));
        userRepository.delete(existingUser);
    }

    @Override
    public UserRole getUserRole(Long id) {
    User user= userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " does not exist."));
        return user.getRole();
    }

    @Override
    public List<UserDTO> findByCriteria(UserForm form) {

        Specification<User> spec = Specification.where(null);

        if (form.getUsername() != null && !form.getUsername().isEmpty()) {
            spec = spec.and(UserSpecification.hasUsername(form.getUsername()));
        }
        if (form.getFirstname() != null && !form.getFirstname().isBlank()) {
            spec = spec.and(UserSpecification.hasFirstname(form.getFirstname()));
        }
        if (form.getLastname() != null && !form.getLastname().isBlank()) {
            spec = spec.and(UserSpecification.hasLastname(form.getLastname()));
        }
        if (form.getEmail() != null && !form.getEmail().isBlank()) {
            spec = spec.and(UserSpecification.hasEmail(form.getEmail()));
        }

        List<User> users = userRepository.findAll(spec);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

}

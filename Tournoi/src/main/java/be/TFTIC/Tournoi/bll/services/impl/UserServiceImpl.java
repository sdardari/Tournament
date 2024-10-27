package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.bll.specifications.UserSpecification;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.dl.entities.Ranking;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.UserRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.User.UserForm;
import be.TFTIC.Tournoi.pl.models.auth.UserRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RankingServiceImpl rankingService;
    private final TeamServiceImpl teamService;

    //region CRUD

    @Override
    public UserDTO createUser(UserRegisterForm userForm) {
        User user = userForm.toEntity();
        userRepository.save(userForm.toEntity());
        return UserDTO.fromEntity(user);
        //        return UserDTO.fromEntity(repo.save(form.toEntity()));
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("Username with this id does not exist"));
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(Long id, UserRegisterForm userForm) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new DoNotExistException("User with id " + id + " doesn't exist."));
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
                new DoNotExistException("Username with id " + id + " doesn't exist."));
        userRepository.delete(existingUser);
    }

    @Override
    public UserRole getUserRole(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DoNotExistException("User with id " + id + " does not exist."));
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

    //endregion

    //region UTILS
    @Override
    public List<User> fromStringToUser(Match match) {
        //TODO FAIRE LA GESTION DES CHAINES DE CARACTERES
        List<User> users = new ArrayList<>();

        for (int i = 0; i < getSize(match.getTeam1Players()); i++) {
            users.add(addUserFromMatchForm(match.getTeam1Players(),i));
            users.add(addUserFromMatchForm(match.getTeam2Players(),i));
        }
        return users;
    }

    @Override
    public int getSize(String team) {
        return getPlayersOfTeam(team).size();
    }

    @Override
    public User addUserFromMatchForm(String team, int id) {
        return getById(parsePlayerId(getPlayersOfTeam(team), id));
    }

    @Override
    public Long parsePlayerId(List<String> teams, int id) {
        return Long.parseLong(teams.get(id));
    }

    @Override
    public List<String> getPlayersOfTeam(String teamId) {
        return Arrays.stream(teamId.split("_")).toList();
    }

    @Override
    public User getById(Long id) {
        StringBuilder sb = new StringBuilder();
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(
                sb.append("User ").append(id).append(" not found.").toString()));
    }
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    //endregion
}

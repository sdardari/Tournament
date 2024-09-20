package be.TFTIC.Tournoi.bll.utils;

import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static List<User> fromStringToUser(MatchForm matchForm, UserRepository userRepository) {
        List<User> users = new ArrayList<>();

        String[] team1 = matchForm.teamId1().split("_");
        String[] team2 = matchForm.teamId2().split("_");

        Long userId1 = Long.parseLong(team1[0]);
        Long userId2 = Long.parseLong(team2[1]);
        Long userId3 = Long.parseLong(team2[0]);
        Long userId4 = Long.parseLong(team1[1]);

        users.add(userRepository.findById(userId1).orElseThrow(() -> new RuntimeException("User 1 not found")));
        users.add(userRepository.findById(userId2).orElseThrow(() -> new RuntimeException("User 2 not found")));
        users.add(userRepository.findById(userId3).orElseThrow(() -> new RuntimeException("User 3 not found")));
        users.add(userRepository.findById(userId4).orElseThrow(() -> new RuntimeException("User 4 not found")));

        return users;
    }
}
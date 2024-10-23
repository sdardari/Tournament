package entities;

import entities.enums.Role;

public class User {
    private int id;
    private String username;
    private Role role;
    public User(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public boolean isPropriaitaire() {
        return role == Role.PROPRIETAIRE;
    }


}
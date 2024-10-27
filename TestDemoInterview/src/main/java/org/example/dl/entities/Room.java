package org.example.dl.entities;

public class Room {
    private int id;
    private String name;
    private int capacity;
    private boolean available;

    public Room(int id, String name, int capacity, boolean available) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.available = available;
    }

}

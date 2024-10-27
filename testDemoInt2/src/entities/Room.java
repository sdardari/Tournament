package entities;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

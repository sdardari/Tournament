package dal;

import entities.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomDAL {
    private List<Room> rooms = new ArrayList<>();

    public RoomDAL() {
        rooms.add(new Room(1, "Salle A", 10, true));
        rooms.add(new Room(2, "Salle B", 20, false));
    }

    public List<Room> getAllRooms() {
        return rooms;
    }

    public Room getRoomById(int roomId) {
        return rooms.stream().filter(room -> room.getId() == roomId).findFirst().orElse(null);
    }

}
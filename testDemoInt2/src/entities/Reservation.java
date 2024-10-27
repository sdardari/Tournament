package entities;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private int roomId;
    private int userId;
    private LocalDateTime reservationTime;

    public Reservation(int id, int roomId, int userId, LocalDateTime reservationTime) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.reservationTime = reservationTime;
    }

}
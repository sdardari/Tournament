package be.TFTIC.Tournoi.pl.models.friendship;

import be.TFTIC.Tournoi.dl.entities.FriendShip;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record FriendShipDTO(
        Long userId,
        Long frienId,
        LocalDateTime startDate
){
    public static FriendShipDTO fromEntity(FriendShip friendShip){
        return new FriendShipDTO(
                friendShip.getUser().getId(),
                friendShip.getFriend().getId(),
                friendShip.getStartDate());
    }
}
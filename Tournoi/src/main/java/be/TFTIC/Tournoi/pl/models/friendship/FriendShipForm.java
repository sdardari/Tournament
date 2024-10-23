package be.TFTIC.Tournoi.pl.models.friendship;

import be.TFTIC.Tournoi.dl.entities.FriendShip;
import be.TFTIC.Tournoi.dl.entities.User;

import java.time.LocalDateTime;

public record FriendShipForm(

) {
    public FriendShip toEntity(User user, User friend, LocalDateTime createDate){
        return new FriendShip(createDate, user, friend);
    }
}
package be.TFTIC.Tournoi.bll.services.service;

import be.TFTIC.Tournoi.dl.entities.FriendShip;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;

import java.util.List;

public interface FriendShipService {

    List<FriendShipDTO> getAll();
    FriendShipDTO getOne(Long id);
    FriendShipDTO addOne(FriendShipForm friendShipForm, Long friendId);
    void delete(Long id);


}

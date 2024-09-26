package be.TFTIC.Tournoi.bll.services;

import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;

import java.util.List;

public interface FriendShipService {
    FriendShipDTO getOne(Long id);

    List<FriendShipDTO> getAll();

    FriendShipDTO addOne(FriendShipForm entity);

    FriendShipDTO delete(Long id);


}

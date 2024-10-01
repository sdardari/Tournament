package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.service.FriendShipService;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendShipServiceImpl implements FriendShipService {
    @Override
    public FriendShipDTO getOne(Long id) {
        return null;
    }

    @Override
    public List<FriendShipDTO> getAll() {
        return List.of();
    }

    @Override
    public FriendShipDTO addOne(FriendShipForm entity) {
        return null;
    }

    @Override
    public FriendShipDTO delete(Long id) {
        return null;
    }
}
package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.exception.exist.DoNotExistException;
import be.TFTIC.Tournoi.bll.services.FriendShipService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dal.repositories.FriendShipRepository;
import be.TFTIC.Tournoi.dl.entities.FriendShip;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendShipServiceImpl implements FriendShipService {

    private final FriendShipRepository friendShipRepository;
    private final UserService userService;

    @Override
    public List<FriendShipDTO> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return friendShipRepository.findAllFriend(user.getId()).stream()
                .map(FriendShipDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public FriendShipDTO getOne(Long id) {
        FriendShip friendShip = friendShipRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("FriendShip with id not found"));
        return FriendShipDTO.fromEntity(friendShip);
    }

    @Override
    public FriendShipDTO addOne(FriendShipForm friendShipForm, Long friendId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User friend = userService.getUserById(friendId);
        if(user.getId().equals(friendId)){
            throw new IllegalArgumentException("impossible de devenir ami avec soi même!");
        }
        boolean existingFriendShip = friendShipRepository.findByUsers(user.getId(), friendId);
        if(existingFriendShip){
            throw new IllegalStateException("L'amitié existe déja !");
        }
        LocalDateTime createDate = LocalDateTime.now();
        return FriendShipDTO.fromEntity(friendShipRepository.save(friendShipForm.toEntity(user, friend, createDate)));
    }

    @Override
    public void delete(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        FriendShip friendShip = friendShipRepository.findById(id)
                .orElseThrow(() -> new DoNotExistException("friendship do not exist"));
        if(!user.getId().equals(friendShip.getUser().getId()) && !user.getId().equals(friendShip.getFriend().getId())){
            throw new RuntimeException("Tu ne peux pas supprimé une amitié qui n'est pas a  toi");
        }
        friendShipRepository.delete(friendShip);
    }

    @Override
    public boolean areFriend(Long userId, Long friendId) {
        return friendShipRepository.findByUsers(userId, friendId);
    }
}
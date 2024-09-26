package be.TFTIC.Tournoi.pl.models.friendship;


import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.FriendShip;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FriendShipForm {

    private final UserRepository userRepository;

    private Long userId;
    private Long friendId;



    public FriendShip toEntity(){
        FriendShip friendShip = new FriendShip();
        friendShip.setUser(userRepository.findById(userId).orElseThrow());
        friendShip.setFriend(userRepository.findById(friendId).orElseThrow());
        return friendShip;
    }


}

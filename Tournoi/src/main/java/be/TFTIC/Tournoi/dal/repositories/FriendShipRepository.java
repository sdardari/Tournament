package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.FriendShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendShipRepository extends JpaRepository<FriendShip,Long> {

    @Query("select f from FriendShip f where f.user.id = :id")
    List<FriendShip> findAllFriend(@Param("id") long id);

    @Query("SELECT count(f)>0 FROM FriendShip f WHERE (f.user.id = :userId AND f.friend.id = :friendId) OR (f.user.id = :friendId AND f.friend.id = :userId)")
    boolean findByUsers(@Param("userId") Long userId, @Param("friendId") Long friendId);

}

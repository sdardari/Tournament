package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter
@ToString
@Table(name= "friendship")
public class FriendShip {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;


    public FriendShip(LocalDateTime createDate, User user, User friend) {
        this.startDate = createDate;
        this.user = user;
        this.friend = friend;
    }

}

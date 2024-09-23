package be.TFTIC.Tournoi.dl.entities;
import be.TFTIC.Tournoi.dl.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long requestId;

    @ManyToOne
    @JoinColumn(name="clan_Id", nullable = false)
    private Clan clan;


    @ManyToOne
    @JoinColumn(name="user_Id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status= RequestStatus.PENDING;



}

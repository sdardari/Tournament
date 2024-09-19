package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter@Setter
@Table(name = "place_of_club")
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String nameClub;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Address address;
}
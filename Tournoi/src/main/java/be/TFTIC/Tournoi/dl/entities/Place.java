package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String nameClub;

    @OneToOne
    private Address address;

    public Place(String nameClub, Address address) {
        this.nameClub = nameClub;
        this.address = address;
    }

}
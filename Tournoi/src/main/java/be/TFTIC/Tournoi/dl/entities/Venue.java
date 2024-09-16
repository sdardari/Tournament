package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venues")
@NoArgsConstructor
@AllArgsConstructor
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venueId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String lieu;

    // One-to-many relation with Tournaments
    @OneToMany(mappedBy = "venue")
    private List<Tournament> tournaments = new ArrayList<>();
}
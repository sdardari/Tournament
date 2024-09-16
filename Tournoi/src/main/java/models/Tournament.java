package models;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String lieu;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    // One-to-many relation with Matches
    @OneToMany(mappedBy = "tournament")
    private List<Match> matches = new ArrayList<>();

    // Many-to-many relation with Teams via Registration
    @OneToMany(mappedBy = "tournament")
    private List<Registration> registrations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "venue_venue_id")
    private Venue venue;

}

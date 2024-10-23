package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tournaments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Column(nullable = false)
    @Range(min = 0)
    private int nbPlace;

    private boolean isCompleted = false;

    private String winner;

    @Enumerated(EnumType.STRING)
    private Division division;

    @Enumerated(EnumType.STRING)
    private TypeTournament typeTournament;

    // Relation ManyToMany avec les équipes participantes
    @ManyToMany
    @JoinTable(
            name = "tournament_team",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> participant = new ArrayList<>();

    // Constructeur utile pour créer un tournoi avec les informations essentielles
    public Tournament(String name, String location, int nbPlace) {
        this.name = name;
        this.location = location;
        this.nbPlace = nbPlace;
        this.participant = new ArrayList<>();
    }
}

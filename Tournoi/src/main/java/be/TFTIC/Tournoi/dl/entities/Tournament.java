package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Table(name = "tournaments")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString @EqualsAndHashCode
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
    @Range(min=0)
    private int nbPlace;

    public Tournament(String name, String location, int nbPlace) {
        this.name = name;
        this.location = location;
        this.nbPlace = nbPlace;
    }
}

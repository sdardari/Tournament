package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "registrations")
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    // Many-to-one relation with Tournament
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    // Many-to-one relation with Team
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "team_1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team_2_id")
    private Team team2;

    private Integer scoreTeam1;
    private Integer scoreTeam2;

    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winner;

    // Many-to-one relation with Tournament
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;
}
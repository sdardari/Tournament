package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;


    @JoinColumn(name = "team_1_id", nullable = false)
    private String team1;


    @JoinColumn(name = "team_2_id", nullable = false)
    private String team2;
    @JoinColumn(name = "team_2_id", nullable = false)
    private String team3;


    @ManyToOne
    @JoinColumn(name="LocationId", nullable = false)
    private Place place;


    @Column(nullable = false)
    private Integer scoreTeam1Set1;
    @Column(nullable = false)
    private Integer scoreTeam2Set1;

    @Column(nullable = false)
    private Integer scoreTeam1Set2;
    @Column(nullable = false)
    private Integer scoreTeam2Set2;


    @Column(nullable = true)
    private Integer scoreTeam2Set3;
    @Column(nullable = true)
    private Integer scoreTeam1Set3;

    private LocalDate dateOfMatch;
}
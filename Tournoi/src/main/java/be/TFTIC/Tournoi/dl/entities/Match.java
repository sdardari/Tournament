package be.TFTIC.Tournoi.dl.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "matches")
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    // Concatenated IDs of players in team 1 and team 2 as strings
    @Column(name = "team_1_players", nullable = false)
    private String team1Players;

    @Column(name = "team_2_players", nullable = false)
    private String team2Players;

    @ManyToOne
    @JoinColumn(name = "LocationId", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "tournament_name", nullable = true)
    private Tournament tournament;

    // Scores for each set
    @Column(nullable = false)
    private Integer scoreTeam1Set1;
    @Column(nullable = false)
    private Integer scoreTeam2Set1;

    @Column(nullable = false)
    private Integer scoreTeam1Set2;
    @Column(nullable = false)
    private Integer scoreTeam2Set2;

    @Column(nullable = true)
    private Integer scoreTeam1Set3;
    @Column(nullable = true)
    private Integer scoreTeam2Set3;

    private LocalDate dateOfMatch;

    public Match(@NotBlank Long teamId1, @NotBlank Long teamId2, @NotBlank Long placeId, Integer scoreTeam1Set1, Integer scoreTeam1Set2, Integer scoreTeam1Set3, Integer scoreTeam2Set1, Integer scoreTeam2Set2, Integer scoreTeam2Set3) {
    }
}
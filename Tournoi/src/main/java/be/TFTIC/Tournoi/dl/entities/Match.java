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
    private Long Id;


    @Column(name = "team_1_players", nullable = false)
    private String team1Players;

    @Column(name = "team_2_players", nullable = false)
    private String team2Players;

    @Column(name = "name_team_1", nullable = true)
    private String nameTeam1;
    @Column(name = "name_team_2", nullable = true)
    private String nameTeam2;
    @Column(name = "name_clan_team_1", nullable = true)
    private String nameClanTeam1;
    @Column(name = "name_Clan_Team_2", nullable = true)
    private String nameClanTeam2;

    @ManyToOne
    @JoinColumn(name = "LocationId", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "tournament_name", nullable = true)
    private Tournament tournament;

    // Scores for each set
    @Column
    private Integer scoreTeam1Set1;
    @Column
    private Integer scoreTeam2Set1;

    @Column
    private Integer scoreTeam1Set2;
    @Column
    private Integer scoreTeam2Set2;

    @Column(nullable = true)
    private Integer scoreTeam1Set3;
    @Column(nullable = true)
    private Integer scoreTeam2Set3;

    private LocalDate dateOfMatch;


    public Match(String teamId1, String teamId2, Long placeId, Integer scoreTeam1Set1, Integer scoreTeam1Set2, Integer scoreTeam1Set3, Integer scoreTeam2Set1, Integer scoreTeam2Set2, Integer scoreTeam2Set3, LocalDate dateOfMatch) {
    }


    public void setPlace(Long aLong) {
    }
}
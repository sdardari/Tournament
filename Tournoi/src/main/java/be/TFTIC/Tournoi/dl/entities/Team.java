package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    // Many-to-many relation with Players
    @ManyToMany(mappedBy = "teams")
    private List<Player> players = new ArrayList<>();

    // One-to-many relation with Matches (Team 1 and Team 2)
    @OneToMany(mappedBy = "team1")
    private List<Match> matchesAsTeam1 = new ArrayList<>();

    @OneToMany(mappedBy = "team2")
    private List<Match> matchesAsTeam2 = new ArrayList<>();

    // Many-to-many relation with Tournaments via Registration
    @OneToMany(mappedBy = "team")
    private List<Registration> registrations = new ArrayList<>();
}
package be.TFTIC.Tournoi.dl.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "tournament_registrations_temp")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TournamentRegisterTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stockage des IDs sous forme de Long
    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "team_id", nullable = true)
    private Long teamId;

    @Column(name = "clan_id", nullable = true)
    private Long clanId;

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "registration_type", nullable = false)
    private String registrationType;

    // Constructeur pour inscription solo
    public TournamentRegisterTemp(Long userId, Long tournamentId, String registrationType) {
        this.userId = userId;
        this.tournamentId = tournamentId;
        this.registrationType = registrationType;
    }

    // Constructeur pour inscription d'équipe ou de clan
    public TournamentRegisterTemp(Long userId, Long teamId, Long clanId, Long tournamentId, String registrationType) {
        this.userId = userId;
        this.teamId = teamId;
        this.clanId = clanId;
        this.tournamentId = tournamentId;
        this.registrationType = registrationType;
    }
}
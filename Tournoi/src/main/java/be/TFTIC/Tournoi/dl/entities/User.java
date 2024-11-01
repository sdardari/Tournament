package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.dl.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "User_")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long Id;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String username;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String firstname;

    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private String lastname;

    @Column(nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ranking_id", referencedColumnName = "id")
    private Ranking ranking;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private UserRole role;

    @ManyToMany(mappedBy = "members")
    private Set<Clan> clans = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Team> teams = new HashSet<>();

    @OneToMany(mappedBy="user")
    private List<FriendShip> friendShips;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public User(String username, String firstname, String lastname, String email, String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    protected void onCreate() {
        if (this.role == null) {
            this.role = UserRole.USER;
        }
        if (this.ranking == null) {
            this.ranking = new Ranking();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

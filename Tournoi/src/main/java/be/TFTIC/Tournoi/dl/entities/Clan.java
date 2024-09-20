package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.dl.enums.CLanRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Clans")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clanId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private boolean isPrivate;


    public boolean getIsPrivate() {
        return isPrivate;
    }

    @Column
    private int minimumTrophies=0;

    @OneToMany(mappedBy = "clan", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> members= new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CLanRole president;


    // Vice Presidents and other roles could be managed in a separate set
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "clan_roles", joinColumns = @JoinColumn(name = "clan_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "role")
    private Map<User, CLanRole> roles = new HashMap<>();


    public Clan (String name, boolean isPrivate, int minimumTrophies){
        this.name=name;
        this.isPrivate=isPrivate;
        this.minimumTrophies=minimumTrophies;

    }

    public boolean canJoin(User user) {
        return !isPrivate && user.getRanking() >= minimumTrophies;
    }

}
package be.TFTIC.Tournoi.dl.entities;

import be.TFTIC.Tournoi.dl.enums.ClanRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "Clans")
@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@EqualsAndHashCode
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clanId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private boolean isPrivate;

    private String president;

    public boolean getIsPrivate() {
        return isPrivate;
    }


    @Column
    private int minimumTrophies=0;

    @ManyToMany
    @JoinTable(
            name = "user_clan",
            joinColumns = @JoinColumn(name = "clan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();



    @ManyToOne
    private Chat chatId;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "clan_roles", joinColumns = @JoinColumn(name = "clan_id"))
    @MapKeyColumn(name ="user_id")
    @Column(name = "role")
    private Map<Long, ClanRole> roles = new HashMap<>();


    public Clan (String name, boolean isPrivate, int minimumTrophies){
        this.name=name;
        this.isPrivate=isPrivate;
        this.minimumTrophies=minimumTrophies;

    }

    public boolean canJoin(User user) {
        return !isPrivate && user.getRanking() >= minimumTrophies;
    }

}
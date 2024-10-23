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
    private String teamId;

    private String name;

    // Many-to-one relation with Players
    @ManyToMany
    @JoinTable(
            name= "users",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

//    public Team(){
//        this.users = new ArrayList<>();
//    }

    @OneToOne
    @JoinColumn(name = "ranking_id", referencedColumnName = "id")
    private Ranking ranking;

    public Team(String name) {
        this.name = name;
    }

    public Team(String teamId, String name, List<User> users) {
        this.users = users;
        this.teamId = teamId;
        this.name = name;
    }
}
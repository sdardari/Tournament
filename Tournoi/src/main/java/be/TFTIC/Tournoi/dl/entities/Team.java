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
    @ManyToOne
    @JoinColumn(name= "user1", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2", nullable = false)
    private User user2;

    public Team(User user1, User user2){
        this.user1=user1;
        this.user2=user2;
    }
}
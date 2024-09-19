package be.TFTIC.Tournoi.dl.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clans")
@NoArgsConstructor
@AllArgsConstructor
public class Clan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clanId;

    @Column(nullable = false)
    private String nomClan;

}
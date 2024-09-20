package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRepository extends JpaRepository<Team, String> {

    @Query("SELECT t FROM Team t JOIN t.users u WHERE u.id = :userId AND SIZE(t.users) = 1")
    Team findSoloTeamByUserId(@Param("userId") Long userId);
}

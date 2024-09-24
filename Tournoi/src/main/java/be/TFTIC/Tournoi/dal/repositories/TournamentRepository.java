package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long>, JpaSpecificationExecutor<Tournament> {

    @Query("select t from Tournament t where t.name ilike :name")
    Tournament getByName(@Param("name") String name);

    @Query("select t from Tournament t join fetch t.participant where t.tournamentId = :tournamentId")
    Optional<Tournament> findTeamByTournament(@Param("tournamentId") Long id);


    //créer une query pour récupérer une liste des match associer au tournoi (join)
}

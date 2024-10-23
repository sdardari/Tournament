package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.Tournament;
import be.TFTIC.Tournoi.dl.enums.Division;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long>, JpaSpecificationExecutor<Tournament> {

    @Query("select t from Tournament t where t.name ilike :name")
    Tournament getByName(@Param("name") String name);

    @Query("select t from Tournament t join fetch t.participant where t.tournamentId = :tournamentId")
    Optional<Tournament> findTeamByTournament(@Param("tournamentId") Long id);

    @Query("SELECT t FROM Tournament t WHERE t.division = :division AND t.typeTournament = :typeTournament AND t.isCompleted = false ORDER BY t.nbPlace DESC")
    List<Tournament> findTournamentsByDivisionAndType(@Param("division") Division division, @Param("typeTournament") TypeTournament typeTournament);
}
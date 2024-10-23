package be.TFTIC.Tournoi.dal.repositories;

import be.TFTIC.Tournoi.dl.entities.TournamentRegisterTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRegisterTempRepository extends JpaRepository<TournamentRegisterTemp, Long> {
    @Query("SELECT trt FROM TournamentRegisterTemp trt WHERE trt.tournamentId = :tournamentId")
    List<TournamentRegisterTemp> findAllByTournamentId(@Param("tournamentId") Long tournamentId);

    @Query("SELECT trt FROM TournamentRegisterTemp trt WHERE trt.tournamentId = :tournamentId AND trt.teamId IS NULL AND trt.clanId IS NULL")
    List<TournamentRegisterTemp> findSoloUsersByTournamentId(@Param("tournamentId") Long tournamentId);
}

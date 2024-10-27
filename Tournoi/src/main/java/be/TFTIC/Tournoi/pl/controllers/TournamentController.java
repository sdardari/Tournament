package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.*;

import be.TFTIC.Tournoi.dl.entities.TournamentRegisterTemp;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import be.TFTIC.Tournoi.pl.models.team.TeamDTO;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentDTO;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterClanForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterSoloForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterTeamForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("tournament")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentRegisterService tournamentRegisterService;

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournament(){
        List<TournamentDTO> tournaments = tournamentService.getAll().stream()
                .map(TournamentDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(tournaments);
    }

    @PostMapping("matchMaking/solo")
    public ResponseEntity<TournamentRegisterSoloForm> matchMakingTournamentSolo(
            @RequestParam Long userId,
            @RequestParam TypeTournament typeTournament) {

        // Appel de la méthode inscriptionSolo pour obtenir le TournamentRegisterTemp
        TournamentRegisterTemp tournamentRegisterTemp = tournamentRegisterService.inscriptionSolo(userId, typeTournament);

        // Conversion en TournamentRegisterSoloForm
        TournamentRegisterSoloForm soloForm = new TournamentRegisterSoloForm();
        soloForm.setUserId1(tournamentRegisterTemp.getUserId());
        soloForm.setTournamentId(tournamentRegisterTemp.getTournamentId());

        return ResponseEntity.ok(soloForm);
    }
    @PostMapping("matchMaking/team")
    public ResponseEntity<TournamentRegisterTeamForm> matchMakingTournamentTeam(
            @RequestParam Long teamId,
            @RequestParam TypeTournament typeTournament) {

        TournamentRegisterTemp tournamentRegisterTemp = tournamentRegisterService.inscriptionTeam(teamId, typeTournament);

        // Conversion en TournamentRegisterSoloForm
        TournamentRegisterTeamForm teamForm = new TournamentRegisterTeamForm();
        teamForm.setTeamId(tournamentRegisterTemp.getUserId());
        teamForm.setTournamentId(tournamentRegisterTemp.getTournamentId());

        return ResponseEntity.ok(teamForm);
    }
    @PostMapping("matchMaking/clan")
    public ResponseEntity<TournamentRegisterClanForm> matchMakingTournamentClan(
            @RequestParam Long clanId,
            @RequestParam TypeTournament typeTournament) {

        // Appel de la méthode inscriptionSolo pour obtenir le TournamentRegisterTemp
        TournamentRegisterTemp tournamentRegisterTemp = tournamentRegisterService.inscriptionClan(clanId, typeTournament);

        // Conversion en TournamentRegisterSoloForm
        TournamentRegisterClanForm clanForm = new TournamentRegisterClanForm();
        clanForm.setClanId(tournamentRegisterTemp.getUserId());
        clanForm.setTournamentId(tournamentRegisterTemp.getTournamentId());

        return ResponseEntity.ok(clanForm);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable long id){
        TournamentDTO tournament = TournamentDTO.fromEntity(tournamentService.getById(id));
        return ResponseEntity.ok(tournament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTournament(@Valid @RequestBody TournamentForm form, @PathVariable long id){
        tournamentService.update(id, form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable long id){
        tournamentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/winner")
    public ResponseEntity<String> winnerTournament(@PathVariable Long id){
        String winner = tournamentService.getWinner(id);

        return ResponseEntity.ok(winner);
    }
}

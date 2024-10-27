package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.*;

import be.TFTIC.Tournoi.dl.entities.TournamentRegisterTemp;
import be.TFTIC.Tournoi.dl.enums.TypeTournament;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentDTO;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterClanForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterSoloForm;
import be.TFTIC.Tournoi.pl.models.tournamentRegisterDTO.TournamentRegisterTeamForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        TournamentRegisterTemp tournamentRegisterTemp = tournamentRegisterService.inscriptionSolo(userId, typeTournament);

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

        TournamentRegisterTeamForm teamForm = new TournamentRegisterTeamForm();
        teamForm.setTeamId(tournamentRegisterTemp.getUserId());
        teamForm.setTournamentId(tournamentRegisterTemp.getTournamentId());

        return ResponseEntity.ok(teamForm);
    }
    @PostMapping("matchMaking/clan")
    public ResponseEntity<TournamentRegisterClanForm> matchMakingTournamentClan(
            @RequestParam Long clanId,
            @RequestParam TypeTournament typeTournament) {

        TournamentRegisterTemp tournamentRegisterTemp = tournamentRegisterService.inscriptionClan(clanId, typeTournament);

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

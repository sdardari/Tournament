package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.TournamentService;

import be.TFTIC.Tournoi.pl.models.team.TeamDTO;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentDTO;
import be.TFTIC.Tournoi.pl.models.tournament.TournamentForm;
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

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAllTournament(){
        List<TournamentDTO> tournaments = tournamentService.getAll().stream()
                .map(TournamentDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(tournaments);
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

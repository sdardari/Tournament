package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.MatchFactory;
import be.TFTIC.Tournoi.bll.services.MatchService;
import be.TFTIC.Tournoi.bll.services.PlaceService;
import be.TFTIC.Tournoi.dl.entities.Match;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchDetailDTO;
import be.TFTIC.Tournoi.pl.models.matchDTO.MatchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final PlaceService placeService;
    private final MatchFactory matchFactory;

    @GetMapping
    public ResponseEntity<List<MatchDetailDTO>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAll().stream()
                .map(MatchDetailDTO::fromMatch).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDetailDTO> getMatchById(@PathVariable Long id) {
        Match match = matchService.getById(id);
        MatchDetailDTO matchDetailDTO = MatchDetailDTO.fromMatch(match);
        return ResponseEntity.ok(matchDetailDTO);
    }
//
//    @PostMapping("/create")
//    public ResponseEntity<MatchDetailDTO> createMatch(
//            @RequestBody @Validated MatchForm matchForm,
//            @RequestParam(required = false) String team1,
//            @RequestParam(required = false) String team2
//    ) {
//
//        Match createdMatch = matchService.createMatch(matchForm, team1, team2);
//        return ResponseEntity.ok(MatchDetailDTO.fromMatch(createdMatch));
//    }
//

    @PutMapping("/{id}")
    public ResponseEntity<MatchDetailDTO> updateMatch(@PathVariable Long id, @RequestBody @Validated MatchForm matchForm) {
        matchService.update(id, matchForm);
        Match updatedMatch = matchService.getById(id);
        MatchDetailDTO matchDetailDTO = MatchDetailDTO.fromMatch(updatedMatch);
        return ResponseEntity.ok(matchDetailDTO);
    }

    @GetMapping("/{id}/result")
    public ResponseEntity<String> getMatchResult(@PathVariable Long id){
        Match match = matchService.getById(id);
        String winner = matchFactory.determineMatchWinner(match);
        return ResponseEntity.ok(winner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
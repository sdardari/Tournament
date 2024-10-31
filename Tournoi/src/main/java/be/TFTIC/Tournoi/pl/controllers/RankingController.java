package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.RankingService;
import be.TFTIC.Tournoi.pl.models.rankingDTO.RankingDetailDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/{id}")
    public ResponseEntity<RankingDetailDTO> getAllRanking(@PathVariable Long id) {
      RankingDetailDTO rankingDetailDTO = RankingDetailDTO.fromEntity(rankingService.getRankingById(id));
      return ResponseEntity.ok(rankingDetailDTO);
    }
}

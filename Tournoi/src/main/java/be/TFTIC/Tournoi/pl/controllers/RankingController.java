package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;
}

package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.RankingService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.rankingDTO.RankingDetailDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RankingController {

}

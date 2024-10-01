package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.service.FriendShipService;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;
    @PostMapping("/add")
    public ResponseEntity<FriendShipDTO> addFriend(@RequestBody FriendShipForm form) {
        return ResponseEntity.ok(friendShipService.addOne(form));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FriendShipDTO> getFriend(@PathVariable Long id) {
        return ResponseEntity.ok(friendShipService.getOne(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<FriendShipDTO>> getAllFriend() {
        return ResponseEntity.ok(friendShipService.getAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<FriendShipDTO> deleteFriend(@PathVariable Long id) {
        return ResponseEntity.ok(friendShipService.delete(id));
    }
}
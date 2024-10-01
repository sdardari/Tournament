package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.service.FriendShipService;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipDTO;
import be.TFTIC.Tournoi.pl.models.friendship.FriendShipForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
@RequiredArgsConstructor
public class FriendShipController {

    private final FriendShipService friendShipService;
    @PostMapping("/add/{id}")
    public ResponseEntity<FriendShipDTO> addFriend(@PathVariable long id) {
        FriendShipForm form = new FriendShipForm();
        return ResponseEntity.ok(friendShipService.addOne(form, id));
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
    public ResponseEntity<Void> deleteFriend(@PathVariable Long id) {
        friendShipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
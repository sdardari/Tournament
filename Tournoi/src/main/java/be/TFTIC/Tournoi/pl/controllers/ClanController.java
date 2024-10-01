package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.service.ClanService;
import be.TFTIC.Tournoi.bll.services.service.JoinRequestService;
import be.TFTIC.Tournoi.bll.services.service.UserService;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.*;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clan")
@RequiredArgsConstructor
public class ClanController {

    private final UserService userService;
    private final ClanService clanService;
    private final JoinRequestService joinRequestService;

    @PostMapping("/create")
    public ResponseEntity<ClanDTO> createClan(
            @Valid @RequestBody ClanFormCreate clanFormCreate,
            Authentication authentication){
        User user = (User) authentication.getPrincipal();
        ClanDTO clanDTO = clanService.createClan(clanFormCreate, user.getId());
      //        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  ResponseEntity.ok(clanDTO);
    }

    // find by id
    @GetMapping("/{id}")
    public ResponseEntity<ClanDTO> getClanById(
            @PathVariable Long id){
        ClanDTO clanDTO = clanService.getClanById(id);
        return ResponseEntity.ok(clanDTO);
    }

    // find all
    @GetMapping
    public ResponseEntity<List<ClanDTO>> getAllClans() {
        List<ClanDTO> clanList = clanService.getAllClans();
        return ResponseEntity.ok(clanList);
    }

    // join
    @PostMapping("/join/{clanId}")
    public ResponseEntity<ClanDTO> joinClan(
            @PathVariable Long clanId,
            Authentication authentication){
        User user= (User) authentication.getPrincipal();
        ClanDTO clanDTO = joinRequestService.joinClan(clanId, user);
        return ResponseEntity.ok(clanDTO);
    }

    //update
    @PutMapping("/edit/{clanId}")
    public ResponseEntity<ClanDTO> updateClan(
            @PathVariable Long clanId,
            @RequestBody ClanFormEdit clanFormEdit,
            Authentication authentication){
        User user= (User) authentication.getPrincipal();
        ClanDTO clanDTO= clanService.updateClan(user,clanId,clanFormEdit);
        return ResponseEntity.ok(clanDTO);
    }

    //delete
    @DeleteMapping("delete/{clanId}")
    public ResponseEntity<MessageDTO> deleteClan(
            @PathVariable Long clanId,
            Authentication authentication){
        User user= (User) authentication.getPrincipal();
        MessageDTO message=clanService.deleteClan(clanId,user);
        return ResponseEntity.ok(message);
    }

    // Leave Clan
    @PostMapping("/leave/{clanId}")
    public ResponseEntity<MessageDTO> leaveClan(
            @PathVariable Long clanId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId= user.getId();
        MessageDTO messageDTO= clanService.leaveClan(clanId, userId);
        return ResponseEntity.ok(messageDTO);
    }


    @PostMapping("/{clanId}/role")
    public  ResponseEntity<MessageDTO> setRole(
            @PathVariable Long clanId,
            @RequestParam Long targetUserId,
            @RequestParam ClanRole newRole,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        UserDTO targetUser = userService.getUserById(targetUserId);
        MessageDTO messageDTO= clanService.setRole(clanId, targetUser, newRole, currentUser);
        return ResponseEntity.ok(messageDTO);
    }

    // Handle Join Request
    @PostMapping("/{clanId}/join-request")
    public ResponseEntity<MessageDTO> handleJoinRequest(
            @PathVariable Long clanId,
            @RequestParam Long userId,
            @RequestParam boolean accept,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        UserDTO user = userService.getUserById(userId);
        MessageDTO messageDTO= joinRequestService.handleJoinRequest(clanId, userId, currentUser, accept);
        return ResponseEntity.ok(messageDTO);
    }

}

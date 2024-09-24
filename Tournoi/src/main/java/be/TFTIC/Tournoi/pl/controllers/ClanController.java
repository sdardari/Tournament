package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.JoinRequest;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.ClanRole;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.*;
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
    private final JwtUtils jwtUtils;

    @PostMapping("/create")
    public ResponseEntity<ClanDTO> createClan(@Valid @RequestBody ClanFormCreate clanFormCreate, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        ClanDTO clanDTO = clanService.createClan(clanFormCreate, user.getId());
      //        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  ResponseEntity.ok(clanDTO);
    }

    // find by id
    @GetMapping("/{id}")
    public ResponseEntity<ClanDTO> getClanById(@PathVariable Long id){
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
    public ResponseEntity<JoinClanDTO> joinClan(@PathVariable Long clanId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        JoinClanDTO jcDTO = clanService.joinClan(clanId, user);
        JoinClanDTO clanDTO = JoinClanDTO.fromEntity(clanService.getById(clanId), jcDTO.message());
        return ResponseEntity.ok(clanDTO);
    }

    //update
    @PutMapping("/edit/{clanId}")
    public ResponseEntity<ClanDTO> updateClan(@PathVariable Long clanId, @RequestBody ClanFormEdit clanFormEdit, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        ClanDTO clanDTO= clanService.updateClan(user,clanId,clanFormEdit);
        return ResponseEntity.ok(clanDTO);
    }

    //delete
    @DeleteMapping("delete/{clanId}")
    public ResponseEntity<String> deleteClan(@PathVariable Long clanId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        clanService.deleteClan(clanId,user);
        return ResponseEntity.ok("Clan deleted succesfully");
    }

    // Leave Clan
    @PostMapping("/leave/{clanId}")
    public ResponseEntity<String> leaveClan(@PathVariable Long clanId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId= user.getId();
        clanService.leaveClan(clanId, userId);
        return ResponseEntity.ok("User left the clan");
    }


    @PostMapping("/{clanId}/role")
    public  ResponseEntity<String> setRole(
            @PathVariable Long clanId,
            @RequestParam Long targetUserId,
            @RequestParam ClanRole newRole,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        UserDTO targetUser = userService.getUserById(targetUserId);
        clanService.setRole(clanId, targetUser, newRole, currentUser);
        return ResponseEntity.ok("Role updated successfully");
    }

    // Handle Join Request
    @PostMapping("/{clanId}/join-request")
    public ResponseEntity<String> handleJoinRequest(
            @PathVariable Long clanId,
            @RequestParam Long userId,
            @RequestParam boolean accept,
            Authentication authentication) {

        // Get the current authenticated user from the security context
        User currentUser = (User) authentication.getPrincipal();

        // Fetch the user making the join request
        UserDTO user = userService.getUserById(userId);

        // Handle the join request using the service method
        clanService.handleJoinRequest(clanId, currentUser, accept);

        // Return appropriate response based on the result
        if (accept) {
            return ResponseEntity.ok("Join request accepted. User has been added to the clan.");
        } else {
            return ResponseEntity.ok("Join request rejected.");
        }
    }



}

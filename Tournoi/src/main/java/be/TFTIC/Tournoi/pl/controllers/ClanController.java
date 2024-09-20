package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.ClanService;
import be.TFTIC.Tournoi.bll.services.UserService;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Clan;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.dl.enums.CLanRole;
import be.TFTIC.Tournoi.il.utils.JwtUtils;
import be.TFTIC.Tournoi.pl.models.User.UserDTO;
import be.TFTIC.Tournoi.pl.models.clan.CLanForm;
import be.TFTIC.Tournoi.pl.models.clan.CLanFormCreate;
import be.TFTIC.Tournoi.pl.models.clan.ClanDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ClanDTO> createClan(@Valid @RequestBody CLanFormCreate cLanFormCreate, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        ClanDTO clanDTO = clanService.createClan(cLanFormCreate, user.getId());
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
    public ResponseEntity<?> joinClan(@PathVariable Long clanId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        ClanDTO clanDTO = ClanDTO.fromEntity(clanService.joinClan(clanId,user));
        return ResponseEntity.ok(clanDTO);
    }

    // Leave Clan
    @PostMapping("/leave/{clanId}")
    public ResponseEntity<?> leaveClan(@PathVariable Long clanId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        clanService.leaveClan(clanId, user);
        return ResponseEntity.ok("User left the clan");
    }

    //update
    @PutMapping("/{clanId}")
    public ResponseEntity<ClanDTO> updateClan(@PathVariable Long clnaId, @RequestBody CLanForm cLanForm, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        ClanDTO clanDTO= clanService.updateClan(clnaId,cLanForm);
        return ResponseEntity.ok(clanDTO);
    }

    //delete
    @DeleteMapping("/{clanId}")
    public ResponseEntity<String> deleteClan(@PathVariable Long clanId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
        clanService.deleteClan(clanId,user);
        return ResponseEntity.ok("Clan deleted succesfully");
    }

    @PostMapping("/{clanId}/role")
    public  ResponseEntity<String> setRole(
            @PathVariable Long clanId,
            @RequestParam Long targetUserId,
            @RequestParam CLanRole newRole,
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

package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.service.TeamService;
import be.TFTIC.Tournoi.bll.services.service.UserService;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.team.TeamDTO;
import be.TFTIC.Tournoi.pl.models.team.TeamForm;
import be.TFTIC.Tournoi.pl.models.team.TeamFormUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<TeamDTO>> getTeam(){
        List<TeamDTO> teamDTOS = teamService.getAllTeams().stream()
                .map(TeamDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable String id){
        TeamDTO team = TeamDTO.fromEntity(teamService.getTeamById(id));
        return ResponseEntity.ok(team);
    }

    @PostMapping("/{friendId}")
    public ResponseEntity<Void> createTeam(@Valid @RequestBody TeamForm form, @PathVariable Long friendId){
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User friend = userRepository.findById(friendId).get();
        String id = authUser.getId() + "_" + friendId;
        Team team = form.toEntity(id, authUser, friend);
        teamService.createTeam(team);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Void> updateTeam(@Valid @RequestBody TeamFormUpdate form, @PathVariable String teamId){
        teamService.updateTeam(teamId, form.toEntity());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String teamId){
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}

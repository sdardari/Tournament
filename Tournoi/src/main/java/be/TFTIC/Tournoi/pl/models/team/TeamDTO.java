package be.TFTIC.Tournoi.pl.models.team;

import be.TFTIC.Tournoi.dl.entities.Team;
import be.TFTIC.Tournoi.pl.models.User.UserParticipantDTO;

import java.util.List;

public record TeamDTO(
        String teamId,
        String name,
        List<UserParticipantDTO> users
) {
    public static TeamDTO fromEntity(Team team){
        List<UserParticipantDTO> userParticipantDTOS = team.getUsers().stream()
                .map(UserParticipantDTO::fromEntity)
                .toList();
        return new TeamDTO(team.getTeamId(), team.getName(), userParticipantDTOS);
    }
}

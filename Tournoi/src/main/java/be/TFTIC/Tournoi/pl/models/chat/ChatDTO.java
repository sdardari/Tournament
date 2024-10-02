package be.TFTIC.Tournoi.pl.models.chat;

import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.pl.models.User.UserParticipantDTO;
import be.TFTIC.Tournoi.pl.models.clan.ClanMemberDTO;
import lombok.Getter;

import java.util.List;


public  record ChatDTO(
        Long id,
        String chatName,
        Long creator,
        List<ClanMemberDTO> members
) {


    public static ChatDTO fromEntity(Chat chat){
        List<ClanMemberDTO> chatMemberDTOS=chat.getParticipants().stream()
                .map(ClanMemberDTO::fromEntity)
                .toList();
        return new ChatDTO(chat.getId(),chat.getChatName(), chat.getCreator(),chatMemberDTOS);
    }
}

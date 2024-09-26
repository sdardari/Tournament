package be.TFTIC.Tournoi.bll.services;


import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;

import java.util.List;

public interface ChatService {
    Chat createChat(Long creatorUserId, Long otherUSerId);
    void addUserToChat(Long chatId, Long userId);
    void removeUserFromChat(Long chatId, Long userId, Long userIdToRemove);
    void renameChat(Long chatId, Long userId, String newChatName);

    MessageChatDTO sendMessage(Long chatId, Long senderId, String content);
    List<MessageChatDTO> getChatMessages(Long chatId);
}

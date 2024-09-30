package be.TFTIC.Tournoi.bll.services;


import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.pl.models.chat.ChatDTO;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {
    Chat findChatById(Long chatId);
    ChatDTO createChat(Long creatorUserId, Long otherUSerId);

    boolean isUserInChat(Long chatId, Long userId);

    MessageDTO addUserToChat(Long chatId, Long userId);

    MessageDTO quitChat(Long chatId, Long userId);

    MessageDTO removeUserFromChat(Long chatId, Long userId, Long userIdToRemove);
    MessageDTO renameChat(Long chatId, Long userId, String newChatName);

    MessageChatDTO sendMessage(Long chatId, Long senderId, String content);
    List<MessageChatDTO> getChatMessages(Long chatId, Long userId);
    Flux<MessageChatDTO> getChatMessagesStream(Long chatId, Long userId);
}

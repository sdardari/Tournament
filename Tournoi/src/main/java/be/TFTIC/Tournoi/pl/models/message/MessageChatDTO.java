

    package be.TFTIC.Tournoi.pl.models.message;

import be.TFTIC.Tournoi.dl.entities.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MessageChatDTO {

    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime localDateTime;

    public static MessageChatDTO fromEntity(ChatMessage message) {

        return new MessageChatDTO(message.getId(),message.getChat().getId(),message.getSender().getId(),message.getContent(),message.getLocalDateTime());

    }
    public static MessageChatDTO createJoinMessage(Long chatId, Long userId, String username) {
        return new MessageChatDTO(null, chatId, userId, "User " + username + " joined the chat.", LocalDateTime.now());
    }

}

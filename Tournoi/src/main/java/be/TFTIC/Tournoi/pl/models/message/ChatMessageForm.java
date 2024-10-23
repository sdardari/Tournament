package be.TFTIC.Tournoi.pl.models.message;

import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.dl.entities.ChatMessage;
import be.TFTIC.Tournoi.dl.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageForm {

    @NotNull(message= "Chat ID cannot be null.")
    private Long chatId;

    @NotNull(message = "Sender ID cannot be null.")
    private Long senderId;

    @NotBlank(message = "Content cannot be empty.")
    private String content;

    public ChatMessage toEntity(Chat chat, User sender){

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChat(chat);
        chatMessage.setSender(sender);
        chatMessage.setContent(this.content);
        chatMessage.setLocalDateTime(LocalDateTime.now());
        return chatMessage;
    }
}

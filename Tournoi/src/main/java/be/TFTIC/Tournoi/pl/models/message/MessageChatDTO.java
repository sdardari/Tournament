

    package be.TFTIC.Tournoi.pl.models.message;

import be.TFTIC.Tournoi.dl.entities.ChatMessage;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageChatDTO {

    private Long id;
    private Long chatId;
    private Long senderId;
    private String content;
    private LocalDateTime localDateTime;

    public MessageChatDTO(ChatMessage message) {

        this.id= message.getId();
        this.chatId= message.getChat().getId();
        this.senderId= message.getSender().getId();
        this.content=message.getContent();
        this.localDateTime=message.getLocalDateTime();
    }
}

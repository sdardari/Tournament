package be.TFTIC.Tournoi.pl.models.message;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter

public class ChatMessageRequestDT0 {
    private Long chatId;
    private Long senderId;
    private String content;
}

package be.TFTIC.Tournoi.pl.controllers;


import be.TFTIC.Tournoi.bll.services.ChatService;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.message.ChatMessageRequestDT0;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private ChatService chatService;

    @MessageMapping("chat.send")
    public Mono<MessageChatDTO> sendMessage(ChatMessageRequestDT0 request) {
        return Mono.fromCallable(() -> chatService.sendMessage(
                request.getChatId(),
                request.getSenderId(),
                request.getContent()));
    }

    // Endpoint for getting all messages for a chat
    @MessageMapping("chat.getMessages")
    public Flux<MessageChatDTO> getChatMessages(Long chatId) {
        return Flux.fromIterable(chatService.getChatMessages(chatId));
    }










    @DeleteMapping("/chat/{chatId}/removeUser/{userId}")
    public ResponseEntity<String> removeUserFromChat(
            @PathVariable Long chatId,
            @PathVariable Long userId,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal(); // Récupérer l'utilisateur actuel
        chatService.removeUserFromChat(chatId, userId, currentUser.getId());
        return ResponseEntity.ok("User removed from chat successfully.");
    }


}

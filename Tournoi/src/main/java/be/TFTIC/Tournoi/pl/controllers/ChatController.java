package be.TFTIC.Tournoi.pl.controllers;

import be.TFTIC.Tournoi.bll.services.ChatService;
import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.chat.ChatDTO;
import be.TFTIC.Tournoi.pl.models.message.ChatMessageForm;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController{

    private final ChatService chatService;

    @MessageMapping("chat.sendMessage")
    public Mono<MessageChatDTO> sendMessage(@Valid @Payload ChatMessageForm chatMessageForm, Authentication authentication){
        Chat chat = chatService.findChatById(chatMessageForm.getChatId());
        User sender = (User) authentication.getPrincipal();
        return Mono.just(chatService.sendMessage(chatMessageForm.getChatId(), sender.getId(), chatMessageForm.getContent()));

    }

    @MessageMapping("chat.subscribe")
    public Flux<MessageChatDTO> subscribeToChat(@Payload Long chatId, Authentication authentication){
        User user= (User) authentication.getPrincipal();
    return chatService.getChatMessagesStream(chatId,user.getId());
    }

    @PostMapping("/createChat")
    public ResponseEntity<ChatDTO> createChat(
            @RequestParam Long otherUserId,
            Authentication authentication){
        User creator = (User) authentication.getPrincipal();
        Long creatorUserId= creator.getId();

        ChatDTO createdChat= chatService.createChat(creatorUserId, otherUserId);
        return  ResponseEntity.ok(createdChat);
    }

    @PostMapping("/addUserToChat/{chatId}")
    public ResponseEntity<MessageDTO> addUserToChat(
            @PathVariable Long chatId,
            @RequestParam Long userToAddId,
            Authentication authentication){

        User currentUser = (User) authentication.getPrincipal();

        if (!chatService.isUserInChat(chatId, currentUser.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        MessageDTO messageDTO = chatService.addUserToChat(chatId, userToAddId);
        return  ResponseEntity.ok(messageDTO);
    }

    @DeleteMapping("/removeUserFromChat/{chatId}")
    public ResponseEntity<MessageDTO> removeUserFromChat(
            @PathVariable Long chatId,
            @RequestParam Long userIdToRemove,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        Long currentUserId = currentUser.getId();

        MessageDTO messageDTO= chatService.removeUserFromChat(chatId, currentUserId, userIdToRemove);

        return ResponseEntity.ok(messageDTO);

    }

    @DeleteMapping("/quitChat/{chatId}")
    public ResponseEntity<MessageDTO> quitChat(
            @PathVariable Long chatId,
            Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        MessageDTO messageDTO = chatService.quitChat(chatId, currentUser.getId());
        return ResponseEntity.ok(messageDTO);
    }


    @PutMapping("/renameChat/{chatId}")
    public ResponseEntity<MessageDTO> renameChat(
            @PathVariable Long chatId,
            @RequestParam String newChatName,
            Authentication authentication) {

        User currentUser = (User) authentication.getPrincipal();
        Long currentUserId = currentUser.getId();

        MessageDTO messageDTO=chatService.renameChat(chatId, currentUserId, newChatName);
        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/messages/{chatId}")
    public ResponseEntity<List<MessageChatDTO>> getChatMessages(
            @PathVariable Long chatId,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        List<MessageChatDTO> messages = chatService.getChatMessages(chatId, user.getId());

        return ResponseEntity.ok(messages);
    }
}




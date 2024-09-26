package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.ChatService;
import be.TFTIC.Tournoi.dal.repositories.ChatRepository;
import be.TFTIC.Tournoi.dal.repositories.ChatMessageRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.dl.entities.ChatMessage;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;

    @Override
    public Chat createChat(Long createrUserId, Long otherUserId) {

        User creator= userRepository.findById(createrUserId)
                .orElseThrow(()-> new RuntimeException("User not found "+ createrUserId));

        User otherUser= userRepository.findById(otherUserId)
                .orElseThrow(()-> new RuntimeException("User not found "+ createrUserId));
        Chat chat = new Chat();
        chat.setParticipants(Set.of(creator,otherUser));
        chat.setCreator(createrUserId);
        chat.setChatName(creator.getUsername() + " & "+ otherUser.getUsername());

        return chatRepository.save(chat);
    }

    @Override
    public void addUserToChat(Long chatId, Long userId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("Chat not found" + chatId));
        User user= userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found "+ userId));

        chat.getParticipants().add(user);
        chatRepository.save(chat);

        if(chat.getParticipants().size()>2){
            StringBuilder chatName = new StringBuilder();
            for( User participant : chat.getParticipants()){
                chatName.append(participant.getUsername()).append(", ");
            }
            chat.setChatName(chatName.substring(0,chatName.length()-2));
            chatRepository.save(chat);
        }
    }

    @Override
    public void removeUserFromChat(Long chatId, Long currentUserId, Long userIdToRemove) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("Chat not found" + chatId));

        User userToRemove = userRepository.findById(userIdToRemove)
                .orElseThrow(() -> new RuntimeException("User not found " + userIdToRemove));

        if(!chat.getCreator().equals(currentUserId)) {
            throw new RuntimeException("Only the chat creator can remove users from the chat.");
        }
        if(chat.getCreator().equals(userToRemove)){
            chat.getParticipants().remove(userToRemove);

            if(!chat.getParticipants().isEmpty()){
                User newCreator = chat.getParticipants().iterator().next();
                chat.setCreator(newCreator.getId());
            }else{
                chatRepository.delete((chat));
                return;
            }
        }else{
            chat.getParticipants().remove(userToRemove);
        }
            chatRepository.save(chat);
    }

    @Override
    public void renameChat(Long chatId, Long userId, String newChatName) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()-> new RuntimeException("Chat not found"));

        if(!chat.getCreator().equals(userId)){
            throw new RuntimeException("Only the chat creator can rename the chat");
        }
        chat.setChatName(newChatName);
        chatRepository.save(chat);
    }

    @Override
    public MessageChatDTO sendMessage(Long chatId, Long senderId, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(()->new RuntimeException("Chat not found" + chatId));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("User not found "+ senderId));

        if (!chat.getParticipants().contains(sender)) {
            throw new RuntimeException("L'utilisateur n'est pas un membre de ce chat.");
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChat(chat);
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setLocalDateTime(LocalDateTime.now());

        messageRepository.save(chatMessage);


    }

    @Override
    public List<MessageChatDTO> getChatMessages(Long chatId) {
        return List.of();
    }
}

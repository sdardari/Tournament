package be.TFTIC.Tournoi.bll.services.impl;

import be.TFTIC.Tournoi.bll.services.ChatService;
import be.TFTIC.Tournoi.dal.repositories.ChatRepository;
import be.TFTIC.Tournoi.dal.repositories.ChatMessageRepository;
import be.TFTIC.Tournoi.dal.repositories.UserRepository;
import be.TFTIC.Tournoi.dl.entities.Chat;
import be.TFTIC.Tournoi.dl.entities.ChatMessage;
import be.TFTIC.Tournoi.dl.entities.User;
import be.TFTIC.Tournoi.pl.models.chat.ChatDTO;
import be.TFTIC.Tournoi.pl.models.message.MessageChatDTO;
import be.TFTIC.Tournoi.pl.models.messageException.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository messageRepository;

    private final Map<Long, Sinks.Many<MessageChatDTO>> chatMessageSinks;


    @Override
    public Chat findChatById(Long chatId) {
        return  chatRepository.findById(chatId)
                .orElseThrow(()-> new RuntimeException("Chat not found with ID: "+ chatId));
    }

    public User findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found: "+ userId));
    }

    @Override
    public ChatDTO createChat(Long createrUserId, Long otherUserId) {

        if (createrUserId.equals(otherUserId)) {
            throw new RuntimeException("You cannot create a chat with yourself.");
        }

        User creator= findUserById(createrUserId);
        User otherUser= findUserById(otherUserId);
        //TODO possibilité de creer plusiers chat entre les meme personne a regler

        Chat chat = new Chat();
        chat.setParticipants(Set.of(creator,otherUser));
        chat.setCreator(createrUserId);
        chat.setChatName(creator.getUsername() + " & "+ otherUser.getUsername());

        Chat createdChat = chatRepository.save(chat);
        //Initialize the sink for real time streaming
        chatMessageSinks.put(createdChat.getId(),Sinks.many().multicast().directBestEffort());

        return ChatDTO.fromEntity(createdChat);
    }

    @Override
    public boolean isUserInChat(Long chatId, Long userId) {

        Chat chat= findChatById(chatId);
        User user= findUserById(userId);

        return chat.getParticipants().contains(user);
    }

    @Override
    public MessageDTO addUserToChat(Long chatId, Long userToAddId) {

        Chat chat= findChatById(chatId);
        User userToAdd= findUserById(userToAddId);
        if(chat.getParticipants().contains(userToAdd)){
            return new MessageDTO(userToAdd.getUsername()+" is already member of the chat");
        }
        chat.getParticipants().add(userToAdd);
        chatRepository.save(chat);

        if(chat.getParticipants().size()>2){
            StringBuilder chatName = new StringBuilder();
            for( User participant : chat.getParticipants()){
                chatName.append(participant.getUsername()).append(", ");
            }
            chat.setChatName(chatName.substring(0,chatName.length()-2));
            chatRepository.save(chat);
        }

        Sinks.Many<MessageChatDTO> messageSink = chatMessageSinks.get(chatId);
        if (messageSink != null) {
            MessageChatDTO joinMessage = MessageChatDTO.createJoinMessage(chatId, userToAdd.getId(), userToAdd.getUsername());
            messageSink.tryEmitNext(joinMessage);}
        return new MessageDTO("User added to chat");
    }

    @Override
    public MessageDTO quitChat(Long chatId, Long userId) {

        Chat chat= findChatById(chatId);
        User user= findUserById(userId);

        return handleUserRemoval(chat, user, " has left the chat");
    }

    @Override
    public MessageDTO removeUserFromChat(Long chatId, Long currentUserId, Long userIdToRemove) {

        Chat chat= findChatById(chatId);
        User userToRemove= findUserById(userIdToRemove);

        if (!chat.getCreator().equals(currentUserId)) {
            throw new RuntimeException("Only the chat creator can remove users from the chat.");
        }
        return handleUserRemoval(chat, userToRemove,  userToRemove.getUsername()+" ejected by Admin ");
    }

    private MessageDTO handleUserRemoval(Chat chat, User user, String message) {

        /// voir fonction au dessus
        if (!isUserInChat(chat.getId(), user.getId())){
            return new MessageDTO("Not member of the chat ");
        }
        chat.getParticipants().remove(user);

        if (chat.getCreator().equals(user.getId())) {
            if (!chat.getParticipants().isEmpty()) {
                User newCreator = chat.getParticipants().iterator().next();
                chat.setCreator(newCreator.getId());
            } else {
                chatRepository.delete(chat);
                chatMessageSinks.remove(chat.getId());
                return new MessageDTO(user.getUsername() + " has left the chat, and the chat has been deleted.");
            }
        }
        chatRepository.save(chat);
        return new MessageDTO(user.getUsername() + message);
    }


    @Override
    public MessageDTO renameChat(Long chatId, Long userId, String newChatName) {
        Chat chat= findChatById(chatId);

        if(!chat.getCreator().equals(userId)){
            return new MessageDTO("Only the chat creator can rename the chat");
            //TODO changer en exception

        }
        chat.setChatName(newChatName);
        chatRepository.save(chat);
        return new MessageDTO("Chat succesfully renamed");
    }

    @Override
    public MessageChatDTO sendMessage(Long chatId, Long senderId, String content) {
        Chat chat= findChatById(chatId);

        User sender= findUserById(senderId);


        if (!chat.getParticipants().contains(sender)) {
            throw new RuntimeException("L'utilisateur n'est pas un membre de ce chat.");
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChat(chat);
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setLocalDateTime(LocalDateTime.now());

        messageRepository.save(chatMessage);

        MessageChatDTO messageChatDTO= MessageChatDTO.fromEntity(chatMessage);

        Sinks.Many<MessageChatDTO> sink = chatMessageSinks.get(chatId);
        if(sink !=null){
            sink.tryEmitNext(messageChatDTO);
        }
        return messageChatDTO;
    }

    @Override
    public List<MessageChatDTO> getChatMessages(Long chatId, Long userId) {
        Chat chat= findChatById(chatId);
        User user= findUserById(userId);

        if (!chat.getParticipants().contains(user)) {
            throw new RuntimeException("L'utilisateur n'est pas un membre de ce chat.");
        }

        List<ChatMessage> chatMessages= messageRepository.findByChat(chat);

        return chatMessages.stream().map(MessageChatDTO::fromEntity).collect(Collectors.toList());

    }

    @Override
    public Flux<MessageChatDTO> getChatMessagesStream(Long chatId, Long userId) {

        Chat chat= findChatById(chatId);
        User user= findUserById(userId);

        if (!chat.getParticipants().contains(user)) {
            throw new RuntimeException("L'utilisateur n'est pas un membre de ce chat.");
        }

        return chatMessageSinks
                .computeIfAbsent(chatId, id -> Sinks.many().multicast().directBestEffort())
                .asFlux();
    }
}

package ntnu.idatt2105.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.MessageDTO;
import ntnu.idatt2105.backend.dto.SendMessageRequest;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.model.Chat;
import ntnu.idatt2105.backend.model.Message;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.ChatRepository;
import ntnu.idatt2105.backend.repository.MessageRepository;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Service class for messages.
 */
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageWebSocketSender messageWebSocketSender;

    /**
     * Constructs a MessageService.
     * 
     * @param messageRepository repository for accessing and registering messages
     * @param chatRepository repository for accessing chats
     * @param userRepository repository for accessing users
     * @param messageWebSocketSender WebSocket sender to broadcast messages in real-time
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository, MessageWebSocketSender messageWebSocketSender) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messageWebSocketSender = messageWebSocketSender;
    }

    /**
     * Creates and saves a new message.
     * 
     * @param messageRequest DTO containing the details of the message
     * @return DTO containing the details of the message that was saved
     * @throws NotFoundException if there was no chat with the specified id or user with the specified username registered
     */
    public MessageDTO sendMessage(SendMessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId()).orElseThrow(() -> new NotFoundException("Chat with id: " + messageRequest.getChatId() + " not found"));
        User sender = userRepository.findByUsername(messageRequest.getSenderUsername()).orElseThrow(() -> new NotFoundException("User with username: " + messageRequest.getSenderUsername() + " not found"));

        Message newMessage = new Message();
        newMessage.setChat(chat);
        newMessage.setSender(sender);
        newMessage.setContent(messageRequest.getContent());
        newMessage.setTimestamp(LocalDateTime.now());

        chat.setTimeLastUpdated(LocalDateTime.now());
        chatRepository.save(chat);
        Message savedMessage = messageRepository.save(newMessage);
        MessageDTO dto = new MessageDTO(savedMessage);

        messageWebSocketSender.sendMessageToChat(dto);
        return dto;
    }

    /**
     * Returns a list of all messages in a chat ordered by the time they were sent.
     * 
     * @param chatId the id of the chat
     * @return List of MessageDTOs with messages from the chat
     */
    public List<MessageDTO> getChatMessages(Long chatId) {
        return messageRepository.findByChatIdOrderByTimestampAsc(chatId).stream()
            .map(MessageDTO::new)
            .collect(Collectors.toList());
    }
}

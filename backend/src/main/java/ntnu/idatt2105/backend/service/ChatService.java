package ntnu.idatt2105.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.ChatDTO;
import ntnu.idatt2105.backend.dto.ChatRequest;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.model.Chat;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.ChatRepository;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Service class for chats.
 */
@Service
public class ChatService {
    
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a ChatService.
     * 
     * @param chatRepository repository to access and register chat info
     * @param userRepository repository to acess user info
     */
    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    /**
     * Creates and saves a new chat if no chat with the specified users already exists. Can be used to get a already existing chat between two users.
     * 
     * @param request DTO containing the usernames of the participants of the chat
     * @return ChatDTO containing chat info
     * @throws IllegalArgumentException if the two usernames are the same
     * @throws NotFoundException if there is no registered user with one of or both of the usernames
     */
    public ChatDTO createChat(ChatRequest request) {
        String user1Username = request.getUser1Username();
        String user2Username = request.getUser2Username();

        if (user1Username.equals(user2Username)) {
            throw new IllegalArgumentException("The users in a chat cannot be the same");
        }

        User user1 = userRepository.findByUsername(user1Username).orElseThrow(() -> new NotFoundException("User with username: " + user1Username + " not found"));
        User user2 = userRepository.findByUsername(user2Username).orElseThrow(() -> new NotFoundException("User with username: " + user2Username + " not found"));

        Optional<Chat> existingChat = chatRepository.findByUsers(user1, user2);
        if (existingChat.isPresent()) {
            return new ChatDTO(existingChat.get());
        }

        Chat newChat = new Chat();
        newChat.setUser1(user1);
        newChat.setUser2(user2);
        newChat.setTimeLastUpdated(LocalDateTime.now());

        Chat savedChat = chatRepository.save(newChat);
        return new ChatDTO(savedChat);
    }

    /**
     * Returns all of the chats the specified user is a part of.
     * 
     * @param username the username of the user
     * @return List of ChatDTOs containing chat info
     * @throws NotFoundException if no user with the specified username is registered
     */
    public List<ChatDTO> getUserChats(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username: " + username + " not found"));

        return chatRepository.findByUser(user).stream()
            .map(ChatDTO::new)
            .collect(Collectors.toList());
    }

    /**
     * Returns an existing chat.
     * 
     * @param request request DTO with usernames of the users in the chat
     * @return ChatDTO containing chat info
     * @throws IllegalArgumentException if the two users are the same
     * @throws NotFoundException if one of the users or the chat is not registered
     */
    public ChatDTO getChat(ChatRequest request) {
        String user1Username = request.getUser1Username();
        String user2Username = request.getUser2Username();

        if (user1Username.equals(user2Username)) {
            throw new IllegalArgumentException("The users in a chat cannot be the same");
        }

        User user1 = userRepository.findByUsername(user1Username).orElseThrow(() -> new NotFoundException("User with username: " + user1Username + " not found"));
        User user2 = userRepository.findByUsername(user2Username).orElseThrow(() -> new NotFoundException("User with username: " + user2Username + " not found"));

        Chat chat = chatRepository.findByUsers(user1, user2).orElseThrow(() -> new NotFoundException("Chat not found"));
        return new ChatDTO(chat);
    }
}

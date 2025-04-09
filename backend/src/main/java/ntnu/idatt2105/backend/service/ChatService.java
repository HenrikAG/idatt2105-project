package ntnu.idatt2105.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ntnu.idatt2105.backend.dto.ChatDTO;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.model.Chat;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.ChatRepository;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Service class for chats.
 */
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
     * @param user1Username username of one of the users
     * @param user2Username username of one of the users
     * @return ChatDTO containing chat info
     * @throws IllegalArgumentException if the two usernames are the same
     * @throws NotFoundException if there is no registered user with one of or both of the usernames
     */
    public ChatDTO createChat(String user1Username, String user2Username) {
        if (user1Username.equals(user2Username)) {
            throw new IllegalArgumentException("The users in a chat must be different");
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
     */
    public List<ChatDTO> getUserChats(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username: " + username + " not found"));

        return chatRepository.findByUser(user).stream()
            .map(ChatDTO::new)
            .collect(Collectors.toList());
    }
}

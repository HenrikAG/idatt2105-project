package ntnu.idatt2105.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt2105.backend.dto.ChatDTO;
import ntnu.idatt2105.backend.dto.ChatRequest;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.service.ChatService;

/**
 * Endpoint for handling HTTP request regartding chats.
 */
@RestController
@RequestMapping("api/chats")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    public final ChatService chatService;

    /**
     * Constructs a ChatController.
     * 
     * @param chatService ChatService to handle logic
     */
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Registers a chat between new users or gets an old chat between the users if it already exists.
     * 
     * @param request DTO containing the username of the two users
     * @return ResponseEntity with the chat if successful, or the exception message otherwise
     */
    @PostMapping
    public ResponseEntity<?> startChat(@RequestBody ChatRequest request) {
        logger.info("Atempting to initialize chat. User1: " + request.getUser1Username() + " User2: " + request.getUser2Username());

        try {
            ChatDTO newChat = chatService.createChat(request);
            return new ResponseEntity<>(newChat, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException exception) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all of the chats a user is a part of.
     * 
     * @param username the username of the user
     * @return ResponseEntity with the chat if successful, otherwise a ResponseEntity with the exception message
     */
    @GetMapping("user/{username}")
    public ResponseEntity<?> getUserChats(@PathVariable String username) {
        try {
            List<ChatDTO> userChats = chatService.getUserChats(username);
            return new ResponseEntity<>(userChats, HttpStatus.OK);
        } catch (NotFoundException exception) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves a chat between two users.
     * 
     * @param request DTO with the username of the two users
     * @return ResponseEntity with the chat if successful, otherwise the exception message
     */
    @GetMapping("/between")
    public ResponseEntity<?> getChatBetweenUsers(@RequestBody ChatRequest request) {
        logger.info("Atempting to retrieve chat between user: " + request.getUser1Username() + " and user: " + request.getUser2Username());

        try {
            ChatDTO chat = chatService.getChat(request);
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException exception) {
            logger.warn(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

package ntnu.idatt2105.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt2105.backend.dto.MessageDTO;
import ntnu.idatt2105.backend.dto.SendMessageRequest;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.service.MessageService;

/**
 * Handles HTTP requests related to messages.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("api/messages")
public class MessageController {
    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    /**
     * Constructs a MessageController.
     * 
     * @param messageService service class to handle logic
     */
    @Autowired
    public MessageController (MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Registers a new message from a user in a chat.
     * 
     * @param request DTO with info about chatId, senderUsername and message content
     * @return ResponseEntity with info regarding the result and the apropriate HttpStatus
     */
    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request) {
        logger.info("Atempting to send message with chatId = " + request.getChatId() + " and senderUsername = " + request.getSenderUsername());

        try {
            MessageDTO message = messageService.sendMessage(request);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (NotFoundException exception) {
            logger.info("Failed to send message: " + exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

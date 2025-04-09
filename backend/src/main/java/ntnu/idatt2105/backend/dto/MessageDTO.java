package ntnu.idatt2105.backend.dto;

import java.time.LocalDateTime;

import ntnu.idatt2105.backend.model.Message;

/**
 * DTO for message data.
 */
public class MessageDTO {
    private final Long id;
    private final Long chatId;
    private final String senderUsername;
    private final String content;
    private final LocalDateTime timestamp;

    /**
     * Maps a Message object to a MessageDTO.
     * 
     * @param message the message to be mapped
     */
    public MessageDTO(Message message) {
        this.id = message.getId();
        this.chatId = message.getChat().getId();
        this.senderUsername = message.getSender().getUsername();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
    }

    /**
     * Returns the id of the chat the message is being sent in.
     * 
     * @return the id of the chat the message is being sent in
     */
    public Long getChatId() {
        return chatId;
    }
}

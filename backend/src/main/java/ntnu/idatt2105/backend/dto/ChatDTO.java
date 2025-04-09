package ntnu.idatt2105.backend.dto;

import java.time.LocalDateTime;

import ntnu.idatt2105.backend.model.Chat;

/**
 * DTO for chat data.
 */
public class ChatDTO {
    private final Long id;
    private final String user1Username;
    private final String user2Username;
    private final LocalDateTime timeLastUpdated;

    /**
     * Maps a Chat object to a ChatDTO object.
     * 
     * @param chat the Chat being mapped
     */
    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.user1Username = chat.getUser1().getUsername();
        this.user2Username = chat.getUser2().getUsername();
        this.timeLastUpdated = chat.getTimeLastUpdated();
    }
}

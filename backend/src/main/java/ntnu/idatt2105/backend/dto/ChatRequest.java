package ntnu.idatt2105.backend.dto;

/**
 * DTO for starting new chats.
 */
public class ChatRequest {
    private final String user1Username;
    private final String user2Username;

    /**
     * Constructs a ChatRequest.
     * 
     * @param user1Username username of one of the participants of the chat
     * @param user2Username username of the other participant of the chat
     */
    public ChatRequest(String user1Username, String user2Username) {
        this.user1Username = user1Username;
        this.user2Username = user2Username;
    }

    /**
     * Returns the username of one of the participants.
     * 
     * @return users username
     */
    public String getUser1Username() {
        return user1Username;
    }

    /**
     * Returns the username of the other participant.
     * 
     * @return users username
     */
    public String getUser2Username() {
        return user2Username;
    }
}

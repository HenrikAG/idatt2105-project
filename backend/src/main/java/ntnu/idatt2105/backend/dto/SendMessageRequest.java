package ntnu.idatt2105.backend.dto;

/**
 * DTO for transfering info for sending new messages.
 */
public class SendMessageRequest {
    private final Long chatId;
    private final String senderUsername;
    private final String content;

    /**
     * Constructs a new SendMessageRequest with the specified parameters.
     * 
     * @param chatId the id of the chat the message is being sent in
     * @param senderUsername the username of the user sending the message
     * @param content the content of the message
     */
    public SendMessageRequest(Long chatId, String senderUsername, String content) {
        this.chatId = chatId;
        this.senderUsername = senderUsername;
        this.content = content;
    }

    /**
     * Returns the id of the chat the message is being sent in
     * 
     * @return the id of the chat the message is being sent in
     */
    public Long getChatId() {
        return chatId;
    }

    /**
     * Returns the username of the user sending the message.
     * 
     * @return the username of the user sending the message
     */
    public String getSenderUsername() {
        return senderUsername;
    }

    /**
     * Returns the content of the message.
     * 
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }
}

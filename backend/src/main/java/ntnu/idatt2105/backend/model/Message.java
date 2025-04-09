package ntnu.idatt2105.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity representing a message from a user.
 */
@Entity
@Table(name = "message")
public class Message {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Constructs a Message.
     */
    public Message() {
    }

    /**
     * Returns the id of the message.
     * 
     * @return the id of the message
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the Chat the message is a part of.
     * 
     * @return the Chat the message is a part of
     */
    public Chat getChat() {
        return chat;
    }

    /**
     * Sets the Chat the message is a part of.
     * 
     * @param chat the Chat the message is a part of
     */
    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * Returns the User who sent the Message.
     * 
     * @return the User who sent the Message
     */
    public User getSender() {
        return sender;
    }

    /**
     * Sets the user sending the message.
     * 
     * @param sender the User sending the message
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * Returns the content of the messsage-
     * 
     * @return the content of the message
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     * 
     * @param content the content of the message
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Returns the time when the message was sent.
     * 
     * @return the time of the message being sent
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value the message was sent.
     * 
     * @param timeStamp the time the message was sent
     */
    public void setTimestamp(LocalDateTime timeStamp) {
        this.timestamp = timeStamp;
    }
}

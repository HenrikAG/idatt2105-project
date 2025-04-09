package ntnu.idatt2105.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity represetning a text chat between two users.
 */
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;
    
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    /**
     * Constructs a Chat.
     */
    public Chat() {
    }

    /**
     * Returns the id of the chat.
     * 
     * @return the id of the chat
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the first user of the chat.
     * 
     * @return the first user in the chat
     */
    public User getUser1() {
        return user1;
    }

    /**
     * Sets the value of the first user of the chat.
     * 
     * @param user1 the first user in the chat
     */
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * Returns the second user of the chat.
     * 
     * @return the second user in the chat
     */
    public User getUser2() {
        return user2;
    }

    /**
     * Sets the value of the second user.
     * 
     * @param user2 the second user of the chat
     */
    public void setUser2(User user2) {
        this.user2 = user2;
    }
}

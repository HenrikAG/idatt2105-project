package ntnu.idatt2105.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import ntnu.idatt2105.backend.dto.MessageDTO;

@Component
public class MessageWebSocketSender {
    private final SimpMessagingTemplate messagingTemplate;

    public MessageWebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToChat(MessageDTO messageDTO) {
        messagingTemplate.convertAndSend("/topic/chat" + messageDTO.getChatId(), messageDTO);
    }
}

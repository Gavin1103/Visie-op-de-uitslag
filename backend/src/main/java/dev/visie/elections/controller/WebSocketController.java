package dev.visie.elections.controller;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.User;
import dev.visie.elections.service.UserService;
import dev.visie.elections.service.WebSocketService;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;

@Controller
public class WebSocketController {

    private final WebSocketService webSocketService;

    public WebSocketController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/chat/{id}")
    @SendTo("/topic/messages/{id}")
    public ChatMessageDTO sendMessage(@Header("Authorization") String token, @Payload ChatMessageDTO chatMessage ) {
        return switch (chatMessage.getType()) {
            case CHAT -> webSocketService.handleChatMessage(token, chatMessage);
            case JOIN -> webSocketService.handleJoinMessage(token, chatMessage);
            case LEAVE -> webSocketService.handleLeaveMessage(token, chatMessage);
            default -> null;
        };
    }
}


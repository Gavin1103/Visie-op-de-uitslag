package dev.visie.elections.controller;

import dev.visie.elections.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class WebSocketController {

    @MessageMapping("/chat/{id}")
    @SendTo("/topic/messages/{id}")
    public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage) {
        chatMessage.setTimestamp(new Date());
        return chatMessage;
    }
}


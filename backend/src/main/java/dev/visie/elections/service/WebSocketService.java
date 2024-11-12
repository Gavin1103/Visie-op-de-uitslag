package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.User;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final UserService userService;


    public WebSocketService(UserService userService) {
        this.userService = userService;
    }

    public ChatMessageDTO handleChatMessage(String Token, ChatMessageDTO message) {
        User user = userService.getUserByToken(Token);
        message.setName(user.getName());
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message.getMessage());
        chatMessage.setUser(user);

        return message;
    }

    public ChatMessageDTO handleJoinMessage(String Token, ChatMessageDTO message) {
        User user = userService.getUserByToken(Token);
        message.setName(user.getName());
        message.setMessage("joined the chat.");
        return message;
    }

    public ChatMessageDTO handleLeaveMessage(String Token, ChatMessageDTO message) {
        User user = userService.getUserByToken(Token);
        message.setName(user.getName());
        message.setMessage("left the chat.");
        return message;
    }
}

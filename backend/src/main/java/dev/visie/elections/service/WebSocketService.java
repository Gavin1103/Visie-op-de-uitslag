package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.User;
import org.springframework.stereotype.Service;
import dev.visie.elections.dto.user.UserProfileResponse;


@Service
public class WebSocketService {

    private final UserService userService;


    public WebSocketService(UserService userService) {
        this.userService = userService;
    }

    public ChatMessageDTO handleChatMessage(String Token, ChatMessageDTO message) {
        UserProfileResponse user= userService.getUserByToken(Token);
        message.setName(user.getUsername());
        return message;
    }

    public ChatMessageDTO handleJoinMessage(String Token, ChatMessageDTO message) {
        UserProfileResponse user = userService.getUserByToken(Token);
        message.setName(user.getUsername());
        message.setMessage("joined the chat.");
        return message;
    }

    public ChatMessageDTO handleLeaveMessage(String Token, ChatMessageDTO message) {
        UserProfileResponse user = userService.getUserByToken(Token);
        message.setName(user.getUsername());
        message.setMessage("left the chat.");
        return message;
    }
}

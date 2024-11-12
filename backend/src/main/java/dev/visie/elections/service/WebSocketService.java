package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dev.visie.elections.dto.user.UserProfileResponse;


@Service
public class WebSocketService {

    private final UserService userService;
    private final ChatMessageService chatMessageService;

    public WebSocketService(UserService userService, ChatMessageService chatMessageService) {
        this.userService = userService;
        this.chatMessageService = chatMessageService;
    }

    public ChatMessageDTO handleChatMessage(String Token, ChatMessageDTO message, Long id) {
        UserProfileResponse user= userService.getUserByToken(Token);

        boolean dbResponse = chatMessageService.addChatMessage(message, id, user.getId());
        if (!dbResponse) {
            message.setMessage(message.getMessage() + " (message not saved)");
        }
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

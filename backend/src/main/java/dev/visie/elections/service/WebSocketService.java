package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import dev.visie.elections.dto.user.UserProfileResponse;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class WebSocketService {

    private final UserService userService;
    private final ChatMessageService chatMessageService;

    private final Map<Long, Set<String>> activeUsers = new ConcurrentHashMap<>();

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
        message.setUserId(user.getId());
        message.setName(user.getUsername());
        return message;
    }

    public ChatMessageDTO handleJoinMessage(String Token, ChatMessageDTO message) {
        UserProfileResponse user = userService.getUserByToken(Token);
        message.setName(user.getUsername());
        message.setMessage("joined the chat.");
        activeUsers.computeIfAbsent(message.getChatId(), k -> new HashSet<>()).add(message.getName());
        message.setActiveUsers(getUserCount(message.getChatId()));
        return message;
    }

    public ChatMessageDTO handleLeaveMessage(String Token, ChatMessageDTO message) {
        UserProfileResponse user = userService.getUserByToken(Token);
        message.setName(user.getUsername());
        message.setMessage("left the chat.");

        Set<String> users = activeUsers.get(message.getChatId());
        if (users != null) {
            users.remove(message.getName());
            if (users.isEmpty()) {
                activeUsers.remove(message.getChatId());
            }
        }
        message.setActiveUsers(getUserCount(message.getChatId()));
        return message;
    }

    public int getUserCount(Long topicId) {
        return activeUsers.getOrDefault(topicId, Collections.emptySet()).size();
    }
}

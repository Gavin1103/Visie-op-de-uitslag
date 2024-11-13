package dev.visie.elections.controller;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.service.ChatMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageService chatMessageService;

    public ChatController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping("/topic/{id}")
    public List<ChatMessageDTO> getLiveChat(@PathVariable Long id) {
        return chatMessageService.getChatMessages(id);
    }
}

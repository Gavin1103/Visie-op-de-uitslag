package dev.visie.elections.controller;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.dto.CreateReportDTO;
import dev.visie.elections.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("report/{id}")
    public ResponseEntity<?> reportChat(@RequestBody CreateReportDTO report, @PathVariable Long id) {
        return chatMessageService.reportChatMesssage(report, id);
    }
}

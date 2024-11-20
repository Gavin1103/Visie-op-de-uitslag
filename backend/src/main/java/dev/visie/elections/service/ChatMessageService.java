package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.dto.CreateReportDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.MessageReport;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.ChatMessageRepository;
import dev.visie.elections.repository.MessageReportRepository;
import dev.visie.elections.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final TopicService topicService;
    private final UserRepository userRepository;
    private final MessageReportRepository messageReportRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              TopicService topicService,
                              UserRepository userRepository,
                              MessageReportRepository messageReportRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.topicService = topicService;
        this.userRepository = userRepository;
        this.messageReportRepository = messageReportRepository;
    }

    public boolean addChatMessage(ChatMessageDTO chatMessageDTO, Long id, Long userId) {
        Topic topic = topicService.getTopicById(id);
        User user = userRepository.getUserById(userId);

        if(topic == null || user == null) {
            return false;
        }
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(chatMessageDTO.getMessage());
        chatMessage.setCreatedAt(chatMessageDTO.getTimestamp());
        chatMessage.setTopic(topic);
        chatMessage.setUser(user);
        chatMessageRepository.save(chatMessage);
        return true;
    }

    public List<ChatMessageDTO> getChatMessages(Long id) {
        return chatMessageRepository.findAllByChatId(id);
    }

    public ResponseEntity<?> reportChatMessage(CreateReportDTO createReportDTO, Long id) {
        ChatMessage chatMessage = chatMessageRepository.findById(id).orElse(null);

        if(chatMessage == null) {
            return ResponseEntity.notFound().build();
        }
        User user = null;
        if(createReportDTO.getReporterId() != null) {
            user = userRepository.getUserById(createReportDTO.getReporterId());
        }

        MessageReport report = new MessageReport();
        report.setCreatedAt(new Date());
        report.setHandled(false);
        report.setReason(createReportDTO.getReason());
        report.setMessage(chatMessage);
        report.setUser(user);
        messageReportRepository.save(report);
        return ResponseEntity.ok(report);



    }
}

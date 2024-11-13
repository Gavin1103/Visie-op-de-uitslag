package dev.visie.elections.service;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.ChatMessageRepository;
import dev.visie.elections.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final TopicService topicService;
    private final UserRepository userRepository;

    public ChatMessageService(ChatMessageRepository chatMessageRepository,
                              TopicService topicService,
                              UserRepository userRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.topicService = topicService;
        this.userRepository = userRepository;
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
}

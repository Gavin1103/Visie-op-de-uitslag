package dev.visie.elections.seeder;


import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.model.User;
import dev.visie.elections.service.ChatMessageService;
import dev.visie.elections.service.TopicService;
import dev.visie.elections.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(5)
@Profile({"local", "dev", "prod"})
public class ChatMessageSeeder  implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageSeeder.class);

    private final JsonSeeder jsonSeeder;
    private final ChatMessageService chatMessageService;


    public ChatMessageSeeder(JsonSeeder jsonSeeder, ChatMessageService chatMessageService, UserService userService) {
        this.jsonSeeder = jsonSeeder;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public void run(String... args) {
        try {
            List<ChatMessageDTO> messages = jsonSeeder.loadData("chat_message.json", ChatMessageDTO.class);

            for (ChatMessageDTO message : messages) {
                if (chatMessageService.chatMessageExists(1L)) {
                    continue;
                }
                try {
                    chatMessageService.addChatMessage(message, 1L, 1L );
                } catch (Exception e) {
                    logger.error("Error creating message", e);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load message data: {}", e.getMessage());
        }
    }
}


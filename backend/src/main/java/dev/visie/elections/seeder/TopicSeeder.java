package dev.visie.elections.seeder;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.User;
import dev.visie.elections.service.TopicService;
import dev.visie.elections.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
@Profile({"local", "dev", "prod"})
public class TopicSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TopicSeeder.class);

    private final JsonSeeder jsonSeeder;
    private final TopicService topicService;
    private final UserService userService;

    public TopicSeeder(JsonSeeder jsonSeeder, TopicService topicService, UserService userService) {
        this.jsonSeeder = jsonSeeder;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        try {
            List<CreateTopicDto> topics = jsonSeeder.loadData("topics.json", CreateTopicDto.class);
            User adminUser = userService.getUserByEmail("admin@admin.com");
            if (adminUser == null) {
                return;
            }

            for (CreateTopicDto topicDto : topics) {
                if (topicService.existsByTitle(topicDto.getMessage())) {
                    continue;
                }
                try {
                    topicService.createTopic(topicDto, adminUser.getEmail());
                } catch (Exception e) {
                    logger.error("Error creating topic '{}': {}", topicDto.getMessage(), e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load topics data: {}", e.getMessage());
        }
    }
}

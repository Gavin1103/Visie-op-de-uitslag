package dev.visie.elections.seeder;

import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.model.RatingData;
import dev.visie.elections.service.TopicRatingService;
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
@Order(4)
@Profile({"local", "dev", "prod"})
public class TopicRatingSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TopicRatingSeeder.class);

    private final JsonSeeder jsonSeeder;
    private final TopicRatingService topicRatingService;
    private final TopicService topicService;
    private final UserService userService;

    public TopicRatingSeeder(JsonSeeder jsonSeeder, TopicRatingService topicRatingService,
                             TopicService topicService, UserService userService) {
        this.jsonSeeder = jsonSeeder;
        this.topicRatingService = topicRatingService;
        this.topicService = topicService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        try {
            List<RatingData> ratings = jsonSeeder.loadData("topic_ratings.json", RatingData.class);
            for (RatingData rating : ratings) {
                try {
                    if (!topicService.existsById(rating.getTopicId())) {
                        continue;
                    }
                    if (!userService.existsByEmail(rating.getUserEmail())) {
                        logger.warn("User with email {} does not exist. Skipping rating.", rating.getUserEmail());
                        continue;
                    }
                    RatingDTO ratingDto = new RatingDTO(rating.getTopicId(), rating.isLike());
                    topicRatingService.createOrUpdateRating(ratingDto, rating.getUserEmail());
                } catch (Exception e) {
                    logger.error("Error processing rating by {} on topic ID {}: {}", rating.getUserEmail(), rating.getTopicId(), e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("Failed to load topic ratings data: {}", e.getMessage());
        }
    }
}

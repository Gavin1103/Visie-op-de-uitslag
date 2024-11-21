package dev.visie.elections.repository;

import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRatingRepository extends RatingRepository<TopicRating> {
    TopicRating getTopicRatingByTopicAndUser(Topic topic, User user);
}

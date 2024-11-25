package dev.visie.elections.repository;

import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRatingRepository extends RatingRepository<TopicRating> {
    TopicRating getTopicRatingByTopicAndUser(Topic topic, User user);

    @Query("SELECT " +
            "SUM(CASE WHEN r.rating = true THEN 1 ELSE 0 END) AS likes, " +
            "SUM(CASE WHEN r.rating = false THEN 1 ELSE 0 END) AS dislikes " +
            "FROM TopicRating r WHERE r.topic.id = :ratingTypeId")
    Object[] countRatings(@Param("ratingTypeId") Long ratingTypeId);

}

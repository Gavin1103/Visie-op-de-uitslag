package dev.visie.elections.repository;

import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRatingRepository extends RatingRepository<TopicRating> {
    TopicRating getTopicRatingByTopicAndUser(Topic topic, User user);

    @Query("SELECT COUNT(tr.rating) FROM TopicRating tr WHERE tr.topic.id = :ratingTypeId AND tr.rating = true ")
    int countLikes(@Param("ratingTypeId") Long ratingTypeId);

    @Query("SELECT COUNT(tr.rating) FROM TopicRating tr WHERE  tr.topic.id = :ratingTypeId AND tr.rating = false ")
    int countDisLikes(@Param("ratingTypeId") Long ratingTypeId);
}

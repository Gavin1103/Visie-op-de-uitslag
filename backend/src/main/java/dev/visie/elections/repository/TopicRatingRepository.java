package dev.visie.elections.repository;

import dev.visie.elections.model.TopicRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRatingRepository extends JpaRepository<TopicRating, Long> {
}

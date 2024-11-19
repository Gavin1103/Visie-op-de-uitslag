package dev.visie.elections.repository;

import dev.visie.elections.model.AnswerRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRatingRepository extends JpaRepository<AnswerRating, Long> {
}

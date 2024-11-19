package dev.visie.elections.repository;

import dev.visie.elections.model.CommentRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRatingRepository extends JpaRepository<CommentRating, Long> {
}

package dev.visie.elections.repository;

import dev.visie.elections.model.Answer;
import dev.visie.elections.model.AnswerRating;
import dev.visie.elections.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRatingRepository extends RatingRepository<AnswerRating> {
    AnswerRating getAnswerRatingByAnswerAndUser(Answer answer, User user);
}

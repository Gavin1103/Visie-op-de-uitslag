package dev.visie.elections.repository;

import dev.visie.elections.model.Comment;
import dev.visie.elections.model.CommentRating;
import dev.visie.elections.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRatingRepository extends RatingRepository<CommentRating> {

    CommentRating getCommentRatingByCommentAndUser(Comment relatedEntity, User user);
}

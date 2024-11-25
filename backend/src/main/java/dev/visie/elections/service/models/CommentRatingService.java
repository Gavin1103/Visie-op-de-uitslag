package dev.visie.elections.service.models;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.model.Comment;
import dev.visie.elections.model.CommentRating;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.CommentRatingRepository;
import dev.visie.elections.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public class CommentRatingService extends RatingService<CommentRating, CommentRatingRepository> {

    private final CommentService commentService;
    private final CommentRatingRepository commentRatingRepository;

    public CommentRatingService(CommentRatingRepository commentRatingRepository, UserService userService, CommentService commentService) {
        super(commentRatingRepository, userService);
        this.commentService = commentService;
        this.commentRatingRepository = commentRatingRepository;
    }

    @Override
    protected Comment getRelatedEntity(Long id) {
        return commentService.getCommentById(id);
    }

    @Override
    protected CommentRating getExistingRating(Object relatedEntity, User user) {
        return commentRatingRepository.getCommentRatingByCommentAndUser((Comment) relatedEntity, user);
    }

    @Override
    protected CommentRating createNewRatingInstance() {
        return new CommentRating();
    }

    @Override
    protected void setRelatedEntity(CommentRating rating, Object relatedEntity) {
        rating.setComment((Comment) relatedEntity);
    }

    @Override
    public AmountOfRatingsDTO getAmountOfRatings(Long ratingTypeId) {
        return null;
    }
}

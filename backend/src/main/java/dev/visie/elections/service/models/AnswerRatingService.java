package dev.visie.elections.service.models;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.model.Answer;
import dev.visie.elections.model.AnswerRating;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.AnswerRatingRepository;
import dev.visie.elections.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AnswerRatingService extends RatingService<AnswerRating, AnswerRatingRepository> {

    private final AnswerService answerService;
    private final AnswerRatingRepository answerRatingRepository;

    public AnswerRatingService(AnswerRatingRepository answerRatingRepository, UserService userService, AnswerService answerService) {
        super(answerRatingRepository, userService);
        this.answerService = answerService;
        this.answerRatingRepository = answerRatingRepository;
    }

    @Override
    protected Answer getRelatedEntity(Long id) {
        return answerService.getAnswerById(id);
    }

    @Override
    protected AnswerRating getExistingRating(Object relatedEntity, User user) {
        return answerRatingRepository.getAnswerRatingByAnswerAndUser((Answer) relatedEntity, user);
    }

    @Override
    protected AnswerRating createNewRatingInstance() {
        return new AnswerRating();
    }

    @Override
    protected void setRelatedEntity(AnswerRating rating, Object relatedEntity) {
        rating.setAnswer((Answer) relatedEntity);
    }

    @Override
    public ResponseEntity<AmountOfRatingsDTO> getAmountOfRatings(Long ratingTypeId) {
        return null;
    }

    @Override
    public ResponseEntity<RatingDTO> hasRating(Long ratingTypeId, String userEmail) {
        return null;
    }
}

package dev.visie.elections.service;

import dev.visie.elections.model.Answer;
import dev.visie.elections.model.AnswerRating;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.AnswerRatingRepository;
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
}

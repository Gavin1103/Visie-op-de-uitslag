package dev.visie.elections.service.models;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;

import dev.visie.elections.repository.TopicRatingRepository;
import dev.visie.elections.repository.TopicRepository;
import dev.visie.elections.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TopicRatingService extends RatingService<TopicRating, TopicRatingRepository> {


    private final TopicRatingRepository topicRatingRepository;
    private final TopicRepository topicRepository;

    public TopicRatingService(TopicRatingRepository topicRatingRepository, UserService userService, TopicRepository topicRepository) {
        super(topicRatingRepository, userService);
        this.topicRepository = topicRepository;
        this.topicRatingRepository = topicRatingRepository;
    }

    @Override
    protected Topic getRelatedEntity(Long id) {
        return topicRepository.getById(id);
    }

    @Override
    protected TopicRating getExistingRating(Object relatedEntity, User user) {
        return topicRatingRepository.getTopicRatingByTopicAndUser((Topic) relatedEntity, user);
    }

    @Override
    protected TopicRating createNewRatingInstance() {
        return new TopicRating();
    }

    @Override
    protected void setRelatedEntity(TopicRating rating, Object relatedEntity) {
        rating.setTopic((Topic) relatedEntity);
    }

    @Override
    public ResponseEntity<AmountOfRatingsDTO> getAmountOfRatings(Long ratingTypeId) {
        int likes = topicRatingRepository.countLikes(ratingTypeId);
        int dislikes = topicRatingRepository.countDisLikes(ratingTypeId);
        AmountOfRatingsDTO ratingsDTO = new AmountOfRatingsDTO(likes, dislikes);

        return ResponseEntity.ok(ratingsDTO);
    }
}


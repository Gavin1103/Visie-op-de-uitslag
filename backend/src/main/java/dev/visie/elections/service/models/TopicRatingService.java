package dev.visie.elections.service.models;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;

import dev.visie.elections.model.base.Rating;
import dev.visie.elections.repository.TopicRatingRepository;
import dev.visie.elections.repository.TopicRepository;
import dev.visie.elections.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TopicRatingService extends RatingService<TopicRating, TopicRatingRepository> {


    private final TopicRatingRepository topicRatingRepository;
    private final TopicRepository topicRepository;
    private final UserService userService;

    public TopicRatingService(TopicRatingRepository topicRatingRepository, UserService userService, TopicRepository topicRepository) {
        super(topicRatingRepository, userService);
        this.topicRepository = topicRepository;
        this.topicRatingRepository = topicRatingRepository;
        this.userService = userService;
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

    @Override
    public ResponseEntity<RatingDTO> hasRating(Long ratingTypeId, String userEmail) {

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Topic topic = topicRepository.findById(ratingTypeId).orElse(null);
        if (topic == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TopicRating topicRating = topicRatingRepository.getTopicRatingByTopicAndUser(topic, user);

        RatingDTO ratingDTO = new RatingDTO(
                topic.getId(),
                topicRating != null ? topicRating.getRating() : null
        );

        return ResponseEntity.ok(ratingDTO);
    }

}


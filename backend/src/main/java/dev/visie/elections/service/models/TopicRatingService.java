package dev.visie.elections.service.models;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.RatingRepository;
import dev.visie.elections.repository.TopicRatingRepository;
import dev.visie.elections.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TopicRatingService extends RatingService<TopicRating, TopicRatingRepository> {

    private final TopicService topicService;
    private final TopicRatingRepository topicRatingRepository;

    public TopicRatingService(TopicRatingRepository topicRatingRepository, UserService userService, TopicService topicService) {
        super(topicRatingRepository, userService);
        this.topicService = topicService;
        this.topicRatingRepository = topicRatingRepository;
    }

    @Override
    protected Topic getRelatedEntity(Long id) {
        return topicService.getTopicById(id);
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
    public AmountOfRatingsDTO getAmountOfRatings(Long ratingTypeId) {
        Object[] result = topicRatingRepository.countRatings(ratingTypeId);

        int likes = ((Number) result[0]).intValue(); // Likes count
        int dislikes = ((Number) result[1]).intValue(); // Dislikes count

        AmountOfRatingsDTO ratings = new AmountOfRatingsDTO(likes, dislikes);

        return ratings;
    }


}


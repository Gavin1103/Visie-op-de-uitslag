package dev.visie.elections.service;

import dev.visie.elections.model.Topic;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.TopicRatingRepository;
import org.springframework.stereotype.Service;

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
}


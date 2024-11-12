package dev.visie.elections.service;

import dev.visie.elections.model.Topic;
import dev.visie.elections.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic getTopicById(Long id){
        return topicRepository.findById(id).orElse(null);
    }
}

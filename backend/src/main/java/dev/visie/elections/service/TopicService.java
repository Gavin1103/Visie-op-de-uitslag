package dev.visie.elections.service;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    @Autowired
    public TopicService(TopicRepository topicRepository, ModelMapper modelMapper, UserService userService) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public Topic createTopic(CreateTopicDto createTopicDto) {

        Topic topic = modelMapper.map(createTopicDto, Topic.class);
        topic.setCreatedAt(new Date());
        topic.setUpdatedAt(new Date());

        return topicRepository.save(topic);
    }

}

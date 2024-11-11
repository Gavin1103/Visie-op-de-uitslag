package dev.visie.elections.service;

import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TopicService(TopicRepository topicRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    public void createTopic(CreateTopicDto createTopicDto) {

        Topic topic = modelMapper.map(createTopicDto, Topic.class);
        topic.setCreatedAt(new Date());
        topic.setUpdatedAt(new Date());

        topicRepository.save(topic);
    }

    public Page<TopicResponseDto> getTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);

        List<TopicResponseDto> topicDtos = topics.getContent().stream()
                .map(topic -> modelMapper.map(topic, TopicResponseDto.class))
                .collect(Collectors.toList());

        return new PageImpl<>(topicDtos, pageable, topics.getTotalElements());
    }
}

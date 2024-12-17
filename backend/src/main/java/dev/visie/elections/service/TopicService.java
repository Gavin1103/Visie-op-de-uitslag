package dev.visie.elections.service;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.topic.CreateTopicDto;
import dev.visie.elections.dto.topic.TopicResponseDto;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.AnswerRepository;
import dev.visie.elections.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TopicRatingService topicRatingService;
    private final AnswerRepository answerRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, ModelMapper modelMapper, UserService userService, TopicRatingService topicRatingService, AnswerRepository answerRepository) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.topicRatingService = topicRatingService;
        this.answerRepository = answerRepository;
    }

    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Topic> createTopic(CreateTopicDto createTopicDto, String userEmail) {
        Topic topic = modelMapper.map(createTopicDto, Topic.class);
        topic.setCreatedAt(new Date());
        topic.setUpdatedAt(new Date());

        User user = userService.getUserByEmail(userEmail);

        if (user != null) {
            topic.setUser(userService.getUserByEmail(userEmail));
        }

        return new ResponseEntity<>(topicRepository.save(topic), HttpStatus.CREATED);
    }

    public Page<TopicResponseDto> getTopics(Pageable pageable, String customSort) {
        Page<Topic> topics = topicRepository.findAll(pageable);

        List<TopicResponseDto> topicDtos = topics.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        if (customSort != null && !customSort.isEmpty()) {
            switch (customSort.toLowerCase()) {
                case "likes" -> topicDtos.sort((dto1, dto2) -> Integer.compare(
                        dto2.getAmountOfRatings().getLikes(),
                        dto1.getAmountOfRatings().getLikes()
                ));
                case "dislikes" -> topicDtos.sort((dto1, dto2) -> Integer.compare(
                        dto2.getAmountOfRatings().getDislikes(),
                        dto1.getAmountOfRatings().getDislikes()
                ));
                default -> throw new IllegalArgumentException("Invalid custom sort value: " + customSort);
            }
        }
        return new PageImpl<>(topicDtos, pageable, topics.getTotalElements());
    }

    public List<TopicResponseDto> searchTopicByStatement(String statement) {
        List<Topic> foundTopics = topicRepository.findByStatementContainingIgnoreCase(statement);

        return foundTopics.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Page<TopicResponseDto> getTopicsByUser(String userEmail, Pageable pageable) {
        User user = userService.getUserByEmail(userEmail);
        Page<Topic> topics = topicRepository.findByUser(user, pageable);

        List<TopicResponseDto> topicDtos = topics.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(topicDtos, pageable, topics.getTotalElements());
    }

    private TopicResponseDto mapToDto(Topic topic) {
        TopicResponseDto dto = modelMapper.map(topic, TopicResponseDto.class);

        AmountOfRatingsDTO ratings = topicRatingService.getAmountOfRatings(topic.getId()).getBody();
        int amountOfAnswers = answerRepository.countAnswersByTopicId(topic.getId());

        dto.setAmountOfRatings(ratings);
        dto.setAmountOfAnswers(amountOfAnswers);

        return dto;
    }

    public Boolean existsByTitle(String title) {
        return topicRepository.existsByMessage(title);
    }

    public Boolean existsById(Long id) {
        return topicRepository.existsById(id);
    }
}

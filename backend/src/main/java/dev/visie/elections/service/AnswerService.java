package dev.visie.elections.service;

import dev.visie.elections.dto.answer.CreateAnswerDto;
import dev.visie.elections.dto.answer.GetAnswerDto;
import dev.visie.elections.model.Answer;
import dev.visie.elections.model.Topic;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.AnswerRepository;
import dev.visie.elections.repository.TopicRepository;
import dev.visie.elections.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, ModelMapper modelMapper, UserRepository userRepository, TopicRepository topicRepository) {
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    public Answer getAnswerById(Long id) {
        return answerRepository.getAnswerById(id);
    }

    public List<GetAnswerDto> getAnswersByTopicId(Long topicId) {
        List<Answer> answers = answerRepository.getAnswersByTopicId(topicId);
        return answers.stream()
                .map(answer -> {
                    GetAnswerDto dto = modelMapper.map(answer, GetAnswerDto.class);
                    dto.setUsername(answer.getUser().getUsername());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Answer addAnswer(CreateAnswerDto createAnswerDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        Topic topic = topicRepository.findById(createAnswerDto.getTopicId()).orElseThrow(() -> new IllegalArgumentException("Topic not found"));

        Answer answer = new Answer();
        answer.setMessage(createAnswerDto.getMessage());
        answer.setUser(user);
        answer.setTopic(topic);

        return answerRepository.save(answer);
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
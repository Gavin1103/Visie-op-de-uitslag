package dev.visie.elections.service;

import dev.visie.elections.model.Answer;
import dev.visie.elections.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }

    public Answer getAnswerById(Long id){
        return answerRepository.getAnswerById(id);
    }
}

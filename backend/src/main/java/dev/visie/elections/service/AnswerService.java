package dev.visie.elections.service;

import dev.visie.elections.model.Answer;
import dev.visie.elections.repository.AnswerRepository;

public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository){
        this.answerRepository = answerRepository;
    }
}

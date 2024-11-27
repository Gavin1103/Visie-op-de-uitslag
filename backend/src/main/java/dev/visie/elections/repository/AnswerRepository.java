package dev.visie.elections.repository;

import dev.visie.elections.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer getAnswerById(Long id);
}

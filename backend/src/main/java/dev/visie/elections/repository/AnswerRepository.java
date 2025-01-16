package dev.visie.elections.repository;

import dev.visie.elections.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer getAnswerById(Long id);

    @Query("SELECT COUNT(a) FROM Answer a WHERE a.topic.id = :topicId")
    int countAnswersByTopicId(@Param("topicId") Long topicId);

    List<Answer> getAnswersByTopicId(Long topicId);
}

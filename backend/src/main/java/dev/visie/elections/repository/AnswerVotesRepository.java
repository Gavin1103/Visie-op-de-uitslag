package dev.visie.elections.repository;

import dev.visie.elections.model.AnswerVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVotesRepository extends JpaRepository<AnswerVotes, Long> {
}

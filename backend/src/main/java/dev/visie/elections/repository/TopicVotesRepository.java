package dev.visie.elections.repository;

import dev.visie.elections.model.TopicVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicVotesRepository extends JpaRepository<TopicVotes, Long> {
}

package dev.visie.elections.repository;

import dev.visie.elections.model.CommentVotes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVotesRepository extends JpaRepository<CommentVotes, Long> {
}

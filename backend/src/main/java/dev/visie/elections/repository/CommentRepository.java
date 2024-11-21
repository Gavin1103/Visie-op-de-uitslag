package dev.visie.elections.repository;

import dev.visie.elections.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment getCommentById(Long id);
}

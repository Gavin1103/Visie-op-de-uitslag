package dev.visie.elections.service.models;

import dev.visie.elections.model.Comment;
import dev.visie.elections.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long id){
        return commentRepository.getCommentById(id);
    }
}

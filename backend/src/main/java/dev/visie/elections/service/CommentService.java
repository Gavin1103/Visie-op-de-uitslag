package dev.visie.elections.service;

import dev.visie.elections.repository.CommentRepository;

public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
}

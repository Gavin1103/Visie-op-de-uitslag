package dev.visie.elections.service;

import dev.visie.elections.dto.rating.CreateRatingDTO;
import dev.visie.elections.model.*;
import dev.visie.elections.model.base.RatingModel;
import dev.visie.elections.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final TopicRatingRepository topicRepository;
    private final AnswerRatingRepository answerRatingRepository;
    private final CommentRatingRepository commentRatingRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CommentService commentService;

    public RatingService(
            TopicRatingRepository topicRatingRepository,
            AnswerRatingRepository answerRatingRepository,
            CommentRatingRepository commentRatingRepository,
            ModelMapper modelMapper,
            UserService userService,
            CommentService commentService
    ) {
        this.topicRepository = topicRatingRepository;
        this.answerRatingRepository = answerRatingRepository;
        this.commentRatingRepository = commentRatingRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.commentService = commentService;
    }

    public ResponseEntity<RatingModel> createRating(CreateRatingDTO createRatingDTO, String userEmail) {

        if(createRatingDTO.getTargetType() === null){
            return null;
        }

        User user = userService.getUserByEmail(userEmail);

        if (createRatingDTO.getTargetType() === "topic") {


        }

        if (createRatingDTO.getTargetType() === "comment") {

        }

        if (createRatingDTO.getTargetType() === "answer") {

        }

        return null;
    }

    private TopicRating createTopicRating(CreateRatingDTO createRatingDTO) {
        return null;
    }

    private CommentRating createCommentRating(CreateRatingDTO createRatingDTO, User user) {
        return null;
    }

    private AnswerRating createAnswerRating(CreateRatingDTO createRatingDTO) {
        return null;
    }
}

package dev.visie.elections.controller;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.model.AnswerRating;
import dev.visie.elections.model.CommentRating;
import dev.visie.elections.model.TopicRating;
import dev.visie.elections.service.JwtService;
import dev.visie.elections.service.models.AnswerRatingService;
import dev.visie.elections.service.models.CommentRatingService;
import dev.visie.elections.service.models.TopicRatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.visie.elections.model.enums.RatingTypeEnum;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final JwtService jwtService;
    private final TopicRatingService topicRatingService;
    private final CommentRatingService commentRatingService;
    private final AnswerRatingService answerRatingService;

    public RatingController(
            JwtService jwtService,
            TopicRatingService topicRatingService,
            CommentRatingService commentRatingService,
            AnswerRatingService answerRatingService
    ) {
        this.jwtService = jwtService;
        this.topicRatingService = topicRatingService;
        this.commentRatingService = commentRatingService;
        this.answerRatingService = answerRatingService;
    }

    @PostMapping("/rate/topic")
    public ResponseEntity<TopicRating> createOrUpdateTopicRating(@RequestBody RatingDTO ratingDTO, HttpServletRequest request) {

        String userEmail = jwtService.extractUserData(request, "sub");
        TopicRating topicRating = topicRatingService.createOrUpdateRating(ratingDTO, userEmail);

        return new ResponseEntity<>(topicRating, HttpStatus.OK);
    }

    @PostMapping("/rate/answer")
    public ResponseEntity<AnswerRating> createOrUpdateAnswerRating(@RequestBody RatingDTO ratingDTO, HttpServletRequest request) {

        String userEmail = jwtService.extractUserData(request, "sub");
        AnswerRating answerRating = answerRatingService.createOrUpdateRating(ratingDTO, userEmail);

        return new ResponseEntity<>(answerRating, HttpStatus.OK);
    }

    @PostMapping("/rate/comment")
    public ResponseEntity<CommentRating> createOrUpdateCommentRating(@RequestBody RatingDTO ratingDTO, HttpServletRequest request) {

        String userEmail = jwtService.extractUserData(request, "sub");
        CommentRating commentRating = commentRatingService.createOrUpdateRating(ratingDTO, userEmail);

        return new ResponseEntity<>(commentRating, HttpStatus.OK);
    }

    @GetMapping("/get-amount/{ratingTypeId}/{ratingType}")
    public AmountOfRatingsDTO getAmountOfRatings(@PathVariable Long ratingTypeId, @PathVariable RatingTypeEnum ratingType) {
        return switch (ratingType) {
            case TOPIC -> topicRatingService.getAmountOfRatings(ratingTypeId);
            case ANSWER -> answerRatingService.getAmountOfRatings(ratingTypeId);
            case COMMENT -> commentRatingService.getAmountOfRatings(ratingTypeId);
        };
    }

}

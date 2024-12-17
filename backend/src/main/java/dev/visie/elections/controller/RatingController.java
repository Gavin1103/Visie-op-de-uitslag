package dev.visie.elections.controller;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.dto.rating.RatingDTO;
import dev.visie.elections.service.JwtService;
import dev.visie.elections.service.AnswerRatingService;
import dev.visie.elections.service.CommentRatingService;
import dev.visie.elections.service.TopicRatingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/rate/{ratingType}")
    @Operation(summary = "Create or update a rating for topic, answer, or comment")
    public ResponseEntity<?> createOrUpdateRating(
            @PathVariable RatingTypeEnum ratingType,
            @RequestBody RatingDTO ratingDTO,
            HttpServletRequest request) {

        String userEmail = jwtService.extractUserData(request, "sub");

        return switch (ratingType) {
            case TOPIC -> ResponseEntity.ok(topicRatingService.createOrUpdateRating(ratingDTO, userEmail));
            case ANSWER -> ResponseEntity.ok(answerRatingService.createOrUpdateRating(ratingDTO, userEmail));
            case COMMENT -> ResponseEntity.ok(commentRatingService.createOrUpdateRating(ratingDTO, userEmail));
        };
    }

    @GetMapping("/get-amount/{ratingTypeId}/{ratingType}")
    @Operation(summary = "count likes and dislikes of topic, comment or answer")
    public ResponseEntity<AmountOfRatingsDTO> getAmountOfRatings(@PathVariable Long ratingTypeId, @PathVariable RatingTypeEnum ratingType) {
        return switch (ratingType) {
            case TOPIC -> topicRatingService.getAmountOfRatings(ratingTypeId);
            case ANSWER -> answerRatingService.getAmountOfRatings(ratingTypeId);
            case COMMENT -> commentRatingService.getAmountOfRatings(ratingTypeId);
        };
    }

    @GetMapping("/has-rating/{ratingTypeId}/{ratingType}")
    @Operation(summary = "Get the rating from topic, comment or likes by user")
    public ResponseEntity<RatingDTO> hasRating(@PathVariable Long ratingTypeId, @PathVariable RatingTypeEnum ratingType, HttpServletRequest request) {
        String userEmail = jwtService.extractUserData(request, "sub");
        return switch (ratingType) {
            case TOPIC -> topicRatingService.hasRating(ratingTypeId, userEmail);
            case ANSWER -> answerRatingService.hasRating(ratingTypeId, userEmail);
            case COMMENT -> commentRatingService.hasRating(ratingTypeId, userEmail);
        };
    }
}

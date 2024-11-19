package dev.visie.elections.controller;

import dev.visie.elections.dto.rating.CreateRatingDTO;
import dev.visie.elections.model.base.RatingModel;
import dev.visie.elections.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping()
    public ResponseEntity<RatingModel> createRating(@RequestBody CreateRatingDTO createRatingDTO, HttpServletRequest request) {

        return null;
    }
}

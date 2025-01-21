package dev.visie.elections.dto.topic;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TopicResponseDto {
    private Long id;

    private String statement;

    private String message;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private AmountOfRatingsDTO amountOfRatings;

    private int amountOfAnswers;

}

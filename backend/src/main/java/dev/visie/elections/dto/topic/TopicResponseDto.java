package dev.visie.elections.dto.topic;

import dev.visie.elections.dto.rating.AmountOfRatingsDTO;
import dev.visie.elections.model.Topic;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TopicResponseDto {
    private Long id;

    private String statement;

    private String message;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    private AmountOfRatingsDTO amountOfRatings;

    private int amountOfAnswers;

}

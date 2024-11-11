package dev.visie.elections.dto.topic;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TopicResponseDto {
    private Long id;

    private String statement;

    private String message;

    private Long userId;

    private String username;

    private Date createdAt;

    private Date updatedAt;

    private int likes;

    private int dislikes;

    private int answerAmount;
}

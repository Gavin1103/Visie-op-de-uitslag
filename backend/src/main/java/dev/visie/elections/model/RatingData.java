package dev.visie.elections.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RatingData {
    private Long topicId;
    private String userEmail;
    private boolean like;
}
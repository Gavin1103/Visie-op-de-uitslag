package dev.visie.elections.dto.topic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class GetTopicDto {
    private String statement;

    @Column(length = 1000)
    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String username;
}

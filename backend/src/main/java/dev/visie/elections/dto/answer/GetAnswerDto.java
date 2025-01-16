package dev.visie.elections.dto.answer;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetAnswerDto {
    private Long id;
    private String message;
    private String username;
    private LocalDateTime createdAt;
}
package dev.visie.elections.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAnswerDto {

    @NotBlank
    private String message;

    @NotNull
    private Long topicId;
}
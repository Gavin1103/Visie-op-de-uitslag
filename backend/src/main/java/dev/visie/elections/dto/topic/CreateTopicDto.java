package dev.visie.elections.dto.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTopicDto {

    private Integer id;

    @NotBlank
    private String statement;

    @NotBlank
    private String message;

    @NotNull
    private Long userId;
}

package dev.visie.elections.dto.topic;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReadTopicDto {

    @NotBlank
    private String title;

    @NotBlank
    private String message;
}

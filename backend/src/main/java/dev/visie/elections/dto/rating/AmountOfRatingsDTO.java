package dev.visie.elections.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AmountOfRatingsDTO {
    int likes;
    int dislikes;
}

package dev.visie.elections.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class RatingDTO {
    Long ratingTypeId;
    Boolean rating;
}

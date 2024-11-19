package dev.visie.elections.model;

import dev.visie.elections.model.base.RatingModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answer_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRating extends RatingModel {

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;
}

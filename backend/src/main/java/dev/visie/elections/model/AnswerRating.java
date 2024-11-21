package dev.visie.elections.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.visie.elections.model.base.Rating;
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
public class AnswerRating extends Rating {

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    @JsonIgnore
    private Answer answer;
}

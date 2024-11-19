package dev.visie.elections.model;

import dev.visie.elections.model.base.RatingModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "topic_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicRating extends RatingModel {

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}
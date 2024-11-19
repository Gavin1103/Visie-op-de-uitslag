package dev.visie.elections.model;

import dev.visie.elections.model.base.RatingModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRating extends RatingModel {
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
}

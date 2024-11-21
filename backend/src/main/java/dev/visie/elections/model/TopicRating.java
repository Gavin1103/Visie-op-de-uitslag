package dev.visie.elections.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.visie.elections.model.base.Rating;
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
public class TopicRating extends Rating {

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonIgnore
    private Topic topic;
}
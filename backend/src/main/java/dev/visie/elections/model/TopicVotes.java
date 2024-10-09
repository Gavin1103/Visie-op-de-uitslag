package dev.visie.elections.model;

import dev.visie.elections.model.base.VoteModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "topic_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicVotes extends VoteModel {

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
}
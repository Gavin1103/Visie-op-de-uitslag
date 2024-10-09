package dev.visie.elections.model;

import dev.visie.elections.model.base.VoteModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answer_votes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerVotes extends VoteModel {

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;
}

package dev.visie.elections.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.visie.elections.model.base.BaseModel;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage extends BaseModel {

    @Column(length = 1000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    @JsonBackReference
    private Topic topic;
}

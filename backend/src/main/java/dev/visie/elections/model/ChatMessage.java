package dev.visie.elections.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.visie.elections.model.base.BaseModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage extends BaseModel {

    @Column(length = 1000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "livechat_id", nullable = false)
    @JsonBackReference
    private LiveChat livechat;
}

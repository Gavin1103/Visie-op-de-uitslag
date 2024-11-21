package dev.visie.elections.model;

import dev.visie.elections.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "message_report")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MessageReport extends BaseModel {

    private String reason;

    @Column(name = "handled")
    private boolean Handled;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private ChatMessage message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

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
    @JoinColumn(name = "handled_by")
    private User handledBy;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private ChatMessage message;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id")
    private User reportedUser;


}

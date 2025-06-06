package dev.visie.elections.dto;
import java.time.LocalDateTime;
import java.util.Date;

import dev.visie.elections.model.enums.MessageTypeEnum;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ChatMessageDTO {
    private Long id;
    private Long userId;
    private Long chatId;
    private String name;
    private String message;
    private LocalDateTime timestamp;
    private int activeUsers;
    private MessageTypeEnum type;

    public ChatMessageDTO(Long id, Long userId, Long chatId, String name,  String message, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.chatId = chatId;
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }

}

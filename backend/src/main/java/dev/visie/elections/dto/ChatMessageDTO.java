package dev.visie.elections.dto;
import java.awt.*;
import java.util.Date;

import dev.visie.elections.model.enums.MessageTypeEnum;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ChatMessageDTO {
    private String name;
    private String message;
    private Date timestamp;
    private MessageTypeEnum type;

}

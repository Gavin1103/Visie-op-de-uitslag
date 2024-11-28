package dev.visie.elections.dto.report;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateReportDTO {
    private String reason;
    private Long reporterId;
    private Long reportedId;
}

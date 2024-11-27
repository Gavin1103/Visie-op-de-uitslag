package dev.visie.elections.dto.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Long id;
    private String reason;
    private Long messageId;
    private String message;
    private Date createdAt;
    private String reporterEmail;
    private String reportedEmail;
}

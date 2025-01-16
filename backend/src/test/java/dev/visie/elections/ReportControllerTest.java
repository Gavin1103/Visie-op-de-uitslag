package dev.visie.elections;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.dto.report.CreateReportDTO;
import dev.visie.elections.dto.report.ReportDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.MessageReport;
import dev.visie.elections.repository.MessageReportRepository;
import dev.visie.elections.service.ChatMessageService;
import dev.visie.elections.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private ReportService reportService;

    private ReportDTO sampleReport;

    @BeforeEach
    public void setUp() {

        CreateReportDTO sampleCreateReport = new CreateReportDTO();
        sampleCreateReport.setReason("Spam");
        sampleCreateReport.setReportedId(1L);
        sampleCreateReport.setReporterId(1L);

        sampleReport = new ReportDTO();
        sampleReport.setId(1L);
        sampleReport.setReason("Spam");
        sampleReport.setMessageId(1L);
        sampleReport.setMessage("This is a spam message");
        sampleReport.setCreatedAt(new Date());
        sampleReport.setReporterEmail("reporter@example.com");
        sampleReport.setReportedEmail("reported@example.com");

        chatMessageService.reportChatMessage(sampleCreateReport, 1L);
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testAllUnhandledReports() throws Exception {
        mockMvc.perform(get("/report/unhandled"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testHandleReport_DisableUserAndDeleteMessage() throws Exception {
        mockMvc.perform(post("/report/handle/true/true")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleReport)))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testHandleReport_NoDisableUserNoDeleteMessage() throws Exception {
        mockMvc.perform(post("/report/handle/false/false")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleReport)))
                .andExpect(status().isOk());
    }

    @AfterEach
    public void tearDown() {
        reportService.deleteReport(sampleReport);
    }
}

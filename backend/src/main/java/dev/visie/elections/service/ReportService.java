package dev.visie.elections.service;

import dev.visie.elections.dto.report.ReportDTO;
import dev.visie.elections.model.ChatMessage;
import dev.visie.elections.model.MessageReport;
import dev.visie.elections.model.User;
import dev.visie.elections.repository.ChatMessageRepository;
import dev.visie.elections.repository.MessageReportRepository;
import dev.visie.elections.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final MessageReportRepository messageReportRepository;
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ReportService(MessageReportRepository messageReportRepository,
                         UserService userService,
                         ChatMessageRepository chatMessageRepository,
                         UserRepository userRepository) {
        this.messageReportRepository = messageReportRepository;
        this.userService = userService;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getUnhandledReports() {
        List<ReportDTO> reports = messageReportRepository.findAllByhandledFalse();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }

    public ResponseEntity<?> handleReport(ReportDTO reportDTO, boolean disableUser, boolean deleteMessage) {
        MessageReport messageReport = messageReportRepository.findById(reportDTO.getId()).orElse(null);
        if(messageReport == null) {
            return ResponseEntity.notFound().build();
        }
        if(disableUser) {
            User user = userService.getUserByEmail(reportDTO.getReportedEmail());
            if(user == null) {
                return ResponseEntity.notFound().build();
            }
            user.setEnabled(false);
            userRepository.save(user);
        }
        if(deleteMessage) {
            ChatMessage chatMessage = chatMessageRepository.findById(reportDTO.getMessageId()).orElse(null);
            if(chatMessage == null) {
                return ResponseEntity.notFound().build();
            }
            chatMessage.setMessage("Bericht is verwijderd.");
            chatMessageRepository.save(chatMessage);
        }
        messageReport.setHandled(true);
        messageReportRepository.save(messageReport);
        return ResponseEntity.ok(messageReport);
    }
}

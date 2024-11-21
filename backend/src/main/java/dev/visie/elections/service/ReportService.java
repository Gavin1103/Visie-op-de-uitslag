package dev.visie.elections.service;

import dev.visie.elections.model.MessageReport;
import dev.visie.elections.repository.MessageReportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    private final MessageReportRepository messageReportRepository;

    public ReportService(MessageReportRepository messageReportRepository) {
        this.messageReportRepository = messageReportRepository;
    }

    public ResponseEntity<?> getUnhandledReports() {
        List<MessageReport> reports = messageReportRepository.findAllByhandledFalse();
        if (reports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reports);
    }
}

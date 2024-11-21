package dev.visie.elections.controller;

import dev.visie.elections.model.MessageReport;
import dev.visie.elections.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/unhandled")
    public ResponseEntity<?> allUnhandled() {
        return reportService.getUnhandledReports();
    }
}

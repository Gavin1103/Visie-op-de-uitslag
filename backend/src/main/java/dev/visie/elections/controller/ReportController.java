package dev.visie.elections.controller;

import dev.visie.elections.dto.report.ReportDTO;
import dev.visie.elections.model.MessageReport;
import dev.visie.elections.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/handle/{disableuser}/{deletemessage}")
    public ResponseEntity<?> handle(@PathVariable("disableuser") boolean disableuser,
                                    @PathVariable("deletemessage") boolean deletemessage,
                                    @RequestBody ReportDTO messageReport) {
        return reportService.handleReport(messageReport, disableuser, deletemessage);
    }
}

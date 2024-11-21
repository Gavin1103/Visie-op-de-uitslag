package dev.visie.elections.repository;

import dev.visie.elections.dto.report.ReportDTO;
import dev.visie.elections.model.MessageReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageReportRepository extends JpaRepository<MessageReport, Long> {
    @Query("SELECT new dev.visie.elections.dto.report.ReportDTO(m.id, m.reason, m.message.message, m.createdAt, m.reporter.email, m.reportedUser.email) " +
            "from MessageReport m " +
            "WHERE m.Handled = false ")
    List<ReportDTO> findAllByhandledFalse();
}

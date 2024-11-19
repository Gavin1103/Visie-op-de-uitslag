package dev.visie.elections.repository;

import dev.visie.elections.model.MessageReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageReportRepository extends JpaRepository<MessageReport, Long> {
}

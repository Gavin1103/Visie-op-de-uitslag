package dev.visie.elections.repository;

import dev.visie.elections.model.MessageReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageReportRepository extends JpaRepository<MessageReport, Long> {
    @Query("SELECT m " +
            "from MessageReport m " +
            "WHERE m.Handled = false ")
    List<MessageReport> findAllByhandledFalse();
}

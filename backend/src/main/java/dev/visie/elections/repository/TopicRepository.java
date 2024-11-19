package dev.visie.elections.repository;

import dev.visie.elections.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findAll(Pageable pageable);
    List<Topic> findByStatementContainingIgnoreCase(String topic);
}

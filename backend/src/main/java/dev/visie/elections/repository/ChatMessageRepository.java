package dev.visie.elections.repository;

import dev.visie.elections.dto.ChatMessageDTO;
import dev.visie.elections.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT new dev.visie.elections.dto.ChatMessageDTO(c.id, c.user.id, c.user.username, c.message, c.createdAt) " +
            "FROM ChatMessage c " +
            "WHERE c.topic.id = :chatId")
    List<ChatMessageDTO> findAllByChatId(@Param("chatId") Long chatId);
}

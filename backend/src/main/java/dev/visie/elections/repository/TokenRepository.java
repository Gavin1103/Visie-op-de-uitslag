package dev.visie.elections.repository;

import dev.visie.elections.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
        SELECT t 
        FROM Token t 
        INNER JOIN User u ON t.user.id = u.id 
        WHERE u.id = :tokenId 
        AND (t.expired = false OR t.revoked = false)
        """)
    List<Token> findAllValidTokenByUser(Long tokenId);

    void deleteByUser_Id(Long id);
}
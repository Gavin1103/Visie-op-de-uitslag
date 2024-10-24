package dev.visie.elections.repository;

import dev.visie.elections.model.election.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {

    @Query("SELECT p.partyId, p.name, p.logo, SUM(v.amount) " +
            "FROM Votes v " +
            "JOIN v.party p " +
            "WHERE p.partyId = :partyId " +
            "GROUP BY p.name, p.logo, p.partyId")
    List<Object[]> getPartyPageInfo(@Param("partyId") int partyId);
}

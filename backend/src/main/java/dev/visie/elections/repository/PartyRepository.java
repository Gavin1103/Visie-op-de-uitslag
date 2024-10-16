package dev.visie.elections.repository;

import dev.visie.elections.dto.party.PartyPageResponse;
import dev.visie.elections.model.election.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Integer> {

    @Query("SELECT new dev.visie.elections.dto.party.PartyPageResponse(p.partyId, p.name, p.logo, SUM(v.amount)) " +
            "FROM Party p " +
            "JOIN Votes v " +
            "WHERE p.partyId = :partyId " +
            "GROUP BY p.partyId, p.name, p.logo")
    PartyPageResponse getPartyPageInfo(@Param("partyId") int partyId);
}

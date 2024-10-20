package dev.visie.elections.repository;

import dev.visie.elections.dto.candidate.CandidateWithVotes;
import dev.visie.elections.model.election.Votes;
import dev.visie.elections.model.election.compositeId.VotesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotesRepository extends JpaRepository<Votes, VotesId> {

    @Query("SELECT new dev.visie.elections.dto.candidate.CandidateWithVotes(c.candidateId, " +
            "CONCAT(CONCAT(CONCAT(c.firstName, ' '), COALESCE(c.lastNamePrefix, '')), ' ', c.lastName), SUM(v.amount)) " +
            "FROM Votes v " +
            "JOIN v.candidate c " +
            "WHERE v.votesId.candidateId.partyId = :partyId " +
            "GROUP BY c.candidateId.candidateId, c.candidateId.partyId, c.firstName, c.lastName, c.lastNamePrefix " +
            "ORDER BY c.candidateId.candidateId")
    List<CandidateWithVotes> getCandidateWithVotes(@Param("partyId") int partyId);
}

package dev.visie.elections.repository;

import dev.visie.elections.dto.candidate.CandidateWithVotes;
import dev.visie.elections.model.election.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotesRepository extends JpaRepository<Votes, Long> {

    @Query("SELECT SUM(v.amount) FROM Votes v")
    int getTotalAmountOfVotes();

    @Query("SELECT p.partyId, p.name, p.logo, SUM(v.amount) " +
            "FROM Votes v JOIN v.party p " +
            "GROUP BY p.name, p.logo, p.partyId " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getPartiesOrderedByVotes();

    @Query("SELECT new dev.visie.elections.dto.candidate.CandidateWithVotes(c.candidateId, " +
            "CONCAT(CONCAT(CONCAT(c.firstName, ' '), COALESCE(c.lastNamePrefix, '')), ' ', c.lastName), SUM(v.amount)) " +
            "FROM Votes v " +
            "JOIN v.candidate c " +
            "WHERE v.votesId.candidateId.partyId = :partyId " +
            "GROUP BY c.candidateId.candidateId, c.candidateId.partyId, c.firstName, c.lastName, c.lastNamePrefix " +
            "ORDER BY SUM(v.amount) DESC")
    List<CandidateWithVotes> getCandidateWithVotes(@Param("partyId") int partyId);
}
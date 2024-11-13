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

    @Query("SELECT c.candidateId.candidateId, c.candidateId.partyId,  c.firstName, c.lastNamePrefix, c.lastName, SUM(v.amount) " +
            "FROM Votes v " +
            "JOIN v.candidate c " +
            "WHERE v.votesId.candidateId.partyId = :partyId " +
            "GROUP BY c.candidateId.candidateId, c.candidateId.partyId, c.firstName, c.lastNamePrefix, c.lastName " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getCandidateWithVotes(@Param("partyId") int partyId);

    @Query("SELECT c.candidateId.candidateId, c.candidateId.partyId,  c.firstName, c.lastNamePrefix, c.lastName, SUM(v.amount) " +
            "FROM Votes v " +
            "JOIN v.candidate c " +
            "JOIN v.station s " +
            "JOIN s.municipality m " +
            "JOIN m.constituency t " +
            "WHERE v.votesId.candidateId.partyId = :partyId " +
            "AND t.name = :searchInput " +
            "GROUP BY c.candidateId.candidateId, c.candidateId.partyId, c.firstName, c.lastNamePrefix, c.lastName " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getCandidateWithVotesAndConstituency(@Param("partyId") int partyId, @Param("searchInput") String searchInput);

    @Query("SELECT c.candidateId.candidateId, c.candidateId.partyId,  c.firstName, c.lastNamePrefix, c.lastName, SUM(v.amount) " +
            "FROM Votes v " +
            "JOIN v.candidate c " +
            "JOIN v.station s " +
            "JOIN s.municipality m " +
            "WHERE v.votesId.candidateId.partyId = :partyId " +
            "AND m.name = :searchInput " +
            "GROUP BY c.candidateId.candidateId, c.candidateId.partyId, c.firstName, c.lastNamePrefix, c.lastName " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getCandidateWithVotesAndMunicipality(@Param("partyId") int partyId, @Param("searchInput") String searchInput);

    @Query("SELECT p.partyId, p.name AS partyName, t.name AS constituencyName, m.name AS municipalityName, SUM(v.amount) " +
            "FROM Votes v " +
            "JOIN v.party p " +
            "JOIN v.station s " +
            "JOIN s.municipality m " +
            "JOIN m.constituency t " +
            "GROUP BY p.partyId, p.name, t.name, m.name " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getTotalVotesByPartyPerConstituencyAndMunicipality();
    
}
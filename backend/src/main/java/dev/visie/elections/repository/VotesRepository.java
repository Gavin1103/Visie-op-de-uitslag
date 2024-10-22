package dev.visie.elections.repository;

import dev.visie.elections.model.election.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VotesRepository extends JpaRepository<Votes, Long> {

    @Query("SELECT SUM(v.amount) FROM Votes v")
    int getTotalAmountOfVotes();

    @Query("SELECT p.partyId, p.name, p.logo, SUM(v.amount) " +
            "FROM Votes v JOIN v.party p " +
            "GROUP BY p.name, p.logo, p.partyId " +
            "ORDER BY SUM(v.amount) DESC")
    List<Object[]> getPartiesOrderedByVotes();
}

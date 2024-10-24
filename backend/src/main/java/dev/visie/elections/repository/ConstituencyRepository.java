package dev.visie.elections.repository;

import dev.visie.elections.model.election.Constituency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstituencyRepository extends JpaRepository<Constituency, Integer> {
}

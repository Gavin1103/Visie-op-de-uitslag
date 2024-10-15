package dev.visie.elections.repository;

import dev.visie.elections.model.election.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Integer> {
}

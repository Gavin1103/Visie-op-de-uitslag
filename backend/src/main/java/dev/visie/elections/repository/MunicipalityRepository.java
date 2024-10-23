package dev.visie.elections.repository;

import dev.visie.elections.model.election.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, String> {
}

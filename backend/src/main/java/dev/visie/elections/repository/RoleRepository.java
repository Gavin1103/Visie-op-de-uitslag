package dev.visie.elections.repository;

import dev.visie.elections.model.Role;
import dev.visie.elections.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByName(RoleEnum name);
}
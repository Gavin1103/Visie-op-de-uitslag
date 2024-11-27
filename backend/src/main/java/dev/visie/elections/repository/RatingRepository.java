package dev.visie.elections.repository;

import dev.visie.elections.model.base.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository<RatingType extends Rating> extends JpaRepository<RatingType, Long> {
}

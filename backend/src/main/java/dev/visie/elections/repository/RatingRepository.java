package dev.visie.elections.repository;

import dev.visie.elections.model.base.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository<RatingType extends Rating> extends JpaRepository<RatingType, Long> {
}

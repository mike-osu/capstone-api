package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByUserIdAndExperienceId(Long userId, Long experienceId);
}

package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByExperienceId(Long experienceId);
}

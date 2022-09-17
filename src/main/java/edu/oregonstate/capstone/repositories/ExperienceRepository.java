package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}

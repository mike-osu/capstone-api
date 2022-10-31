package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {

    List<Trip> findByExperiencesId(Long experienceId);

    List<Trip> findByUserId(Long userId);
}

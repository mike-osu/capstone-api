package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query(value = "SELECT e FROM Experience e WHERE fts(:keyword, :keyword, :keyword, :keyword, :keyword) = true")
    List<Experience> findByKeyword(@Param("keyword") String keyword);

    List<Experience> findByUserId(Long userId);

    List<Experience> findByTripsId(Long tripId);
}
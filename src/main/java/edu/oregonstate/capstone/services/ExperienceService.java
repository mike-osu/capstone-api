package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Experience;

import java.util.List;

public interface ExperienceService {

    Experience findById(Long id);

    List<Experience> getAll();

    List<Experience> findByUserId(Long userId);

    List<Experience> findByKeyword(String keyword);

    List<Experience> findByTripId(Long tripId);

    Experience save(Experience experience);

    Experience save(Experience experience, boolean saveCoordinates);

    void delete(Long id);
}

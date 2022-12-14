package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Rating;

public interface RatingService {

    Rating save(Rating rating);

    Rating get(Long userId, Long experienceId);

    void deleteByExperienceId(Long experienceId);
}

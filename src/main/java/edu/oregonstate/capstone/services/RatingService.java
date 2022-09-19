package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Rating;

import java.util.List;

public interface RatingService {

    Rating save(Rating rating);

    double getAverageRating(Long experienceId);
}

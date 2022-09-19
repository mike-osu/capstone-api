package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Rating;
import edu.oregonstate.capstone.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public double getAverageRating(Long experienceId) {

        List<Rating> ratings = ratingRepository.findByExperienceId(experienceId);

        return ratings.stream()
                .collect(Collectors.averagingDouble(Rating::getStarCount));
    }
}

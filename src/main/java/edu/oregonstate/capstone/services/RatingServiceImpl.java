package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Rating;
import edu.oregonstate.capstone.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }
}

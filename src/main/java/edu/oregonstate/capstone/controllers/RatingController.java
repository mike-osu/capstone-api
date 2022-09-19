package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Rating;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.RatingService;
import edu.oregonstate.capstone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    ExperienceService experienceService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Enter a user's rating for an experience")
    @PostMapping("/ratings/create")
    public ResponseEntity<String> createRating(@RequestBody Rating rating) {

        try {
            Experience experience = experienceService.findById(rating.getExperienceId());
            if (experience == null) {
                return new ResponseEntity<>("experience not found", HttpStatus.NOT_FOUND);
            }

            User user = userService.findById(rating.getUserId());
            if (user == null) {
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }

            if (rating.getStarCount() < 0 || rating.getStarCount() > 5) {
                return new ResponseEntity<>("invalid rating (must be between 1 and 5 inclusive)", HttpStatus.BAD_REQUEST);
            }

            ratingService.save(rating);
            return new ResponseEntity<>("rating saved", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

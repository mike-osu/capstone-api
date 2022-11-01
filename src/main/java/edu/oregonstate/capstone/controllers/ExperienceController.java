package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.aws.AmazonClient;
import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.RatingService;
import edu.oregonstate.capstone.services.TripService;
import edu.oregonstate.capstone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    @Autowired
    TripService tripService;

    @Autowired
    AmazonClient amazonClient;

    @ApiOperation(value = "Get an experience by id", notes = "http://{base_url}/experiences/1")
    @GetMapping("/experiences/{id}")
    public ResponseEntity<Experience> get(@PathVariable("id") Long id) {
        return Optional.ofNullable(experienceService.findById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get all experiences")
    @GetMapping("/experiences/all")
    public ResponseEntity<List<Experience>> getAll() {

        List<Experience> experiences = experienceService.getAll();
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Get experiences by user")
    @GetMapping("/users/{userId}/experiences")
    public ResponseEntity<List<Experience>> getAllForUser(@PathVariable("userId") Long userId) {

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Experience> experiences = experienceService.findByUserId(userId);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Find experiences by keyword", notes = "http://{base_url}/experiences/search?keyword=europe")
    @GetMapping("/experiences/search")
    public ResponseEntity<List<Experience>> searchByKeyword(@RequestParam("keyword") String keyword) {

        List<Experience> experiences = experienceService.findByKeyword(keyword);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Get experiences for a trip")
    @GetMapping("/trips/{tripId}/experiences")
    public ResponseEntity<List<Experience>> getAllForTrip(@PathVariable("tripId") Long tripId) {

        Trip trip = tripService.findById(tripId);
        if (trip == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Experience> experiences = experienceService.findByTripId(tripId);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Create an experience for a user")
    @PostMapping("/users/{userId}/experiences")
    public ResponseEntity<Experience> createExperience(@PathVariable(value = "userId") Long userId,
                                                       @RequestBody Experience experienceRequest) {

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        experienceRequest.setUser(user);
        Experience experience = experienceService.save(experienceRequest);

        return new ResponseEntity<>(experience, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an experience")
    @PutMapping("/experiences/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable(value = "id") Long id,
                                                       @RequestBody Experience experienceRequest) {

        Experience experience = experienceService.findById(id);
        if (experience == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        experience.setTitle(experienceRequest.getTitle());
        experience.setDescription(experienceRequest.getDescription());
        experience.setCity(experienceRequest.getCity());
        experience.setState(experienceRequest.getState());
        experience.setCountry(experienceRequest.getCountry());
        experienceService.save(experience);

        return new ResponseEntity<>(experience, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an experience by id", notes = "http://{base_url}/experiences/1")
    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {
            Experience exp = experienceService.findById(id);
            if (exp == null) {
                return new ResponseEntity<>("Experience not found", HttpStatus.NOT_FOUND);
            }

            exp.checkExpAssociationBeforeRemoval();

            ratingService.deleteByExperienceId(id);
            experienceService.delete(id);
            return new ResponseEntity<>("Experience deleted", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Upload an experience image", notes = "Content-Type: multipart/form-data")
    @PostMapping("/experiences/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable("id") Long id,
                                              @RequestPart(value = "file") MultipartFile multipartFile) {

        try {
            String imageUrl = this.amazonClient.uploadFile(multipartFile, id);
            updateImageUrl(imageUrl, id);
            return new ResponseEntity<>("image uploaded: " + imageUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateImageUrl(String url, Long id) {
        Experience experience = experienceService.findById(id);
        experience.setImageUrl(url);
        experienceService.save(experience);
    }
}

package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.RatingService;
import edu.oregonstate.capstone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExperienceController {
    
    @Autowired
    ExperienceService experienceService;

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    @ApiOperation(value = "Get an experience by id", notes = "http://{base_url}/experiences/1")
    @GetMapping("/experiences/{id}")
    public ResponseEntity<Experience> get(@PathVariable("id") Long id) {
        Experience experience = experienceService.findById(id);

        if (experience == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(experience, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all experiences")
    @GetMapping("/experiences/all")
    public ResponseEntity<List<Experience>> getAll() {

        List<Experience> experiences = experienceService.getAll();
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Find experiences by keyword", notes = "http://{base_url}/experiences/search?keyword=europe")
    @GetMapping("/experiences/search")
    public ResponseEntity<List<Experience>> searchByKeyword(@RequestParam("keyword") String keyword) {

        List<Experience> experiences = experienceService.findByKeyword(keyword);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @ApiOperation(value = "Create an experience for a user")
    @PostMapping("/users/{userId}/experiences")
    public ResponseEntity<Experience> createExperience(@PathVariable(value = "userId") Long userId,
                                                       @RequestBody Experience experienceRequest) {

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Long id) {
        experienceService.delete(id);
        return "deleted";
    }
}

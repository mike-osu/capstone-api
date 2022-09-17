package edu.oregonstate.capstone.controllers;

import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.UserService;
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

    @GetMapping("/experiences/{id}")
    public ResponseEntity<Experience> get(@PathVariable("id") Long id) {
        Experience experience = experienceService.findById(id);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }

    @GetMapping("/experiences/all")
    public ResponseEntity<List<Experience>> getAll() {
        List<Experience> experiences = experienceService.getAll();
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

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

    @DeleteMapping("/experiences/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable("id") Long id) {
        experienceService.delete(id);
        return "deleted";
    }
}

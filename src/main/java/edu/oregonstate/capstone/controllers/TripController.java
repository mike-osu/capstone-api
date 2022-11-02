package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.TripService;
import edu.oregonstate.capstone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @Autowired
    ExperienceService experienceService;

    @ApiOperation(value = "Get all trips")
    @GetMapping("/trips/all")
    public ResponseEntity<List<Trip>> getAll() {

        List<Trip> trips = tripService.getAll();
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a trip by id")
    @GetMapping("/trips/{tripId}")
    public ResponseEntity<Trip> get(@PathVariable("tripId") Long id) {
        return Optional.ofNullable(tripService.findById(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Get trips by user")
    @GetMapping("/users/{userId}/trips")
    public ResponseEntity<List<Trip>> getAllForUser(@PathVariable("userId") Long userId) {

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        List<Trip> trips = tripService.findByUserId(userId);
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a trip for a user")
    @PostMapping("/users/{userId}/trips")
    public ResponseEntity<Trip> createTrip(@PathVariable(value = "userId") Long userId,
                                                       @RequestBody Trip tripRequest) {

        User user = userService.findById(userId);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        tripRequest.setUser(user);
        Trip trip = tripService.save(tripRequest);

        return new ResponseEntity<>(trip, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add an experience to a trip")
    @PutMapping("/trips/{tripId}/experiences/{experienceId}")
    public ResponseEntity<Experience> addExperience(@PathVariable("tripId") Long tripId,
                                                    @PathVariable("experienceId") Long experienceId) {

        Trip trip = tripService.findById(tripId);
        Experience experience = experienceService.findById(experienceId);
        if ((trip == null) || (experience == null)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Experience exists = trip.getExperiences().stream()
                .filter(exp -> Objects.equals(exp.getId(), experienceId))
                .findFirst()
                .orElse(null);
        if (exists != null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        trip.getExperiences().add(experience);
        tripService.save(trip);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }

    @ApiOperation(value = "Remove an experience from a trip")
    @DeleteMapping("/trips/{tripId}/experiences/{experienceId}")
    public ResponseEntity<Long> removeExperience(@PathVariable("tripId") Long tripId,
                                                    @PathVariable("experienceId") Long experienceId) {

        Trip trip = tripService.findById(tripId);
        Experience experience = experienceService.findById(experienceId);
        if ((trip == null) || (experience == null)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Experience exists = trip.getExperiences().stream()
                .filter(exp -> Objects.equals(exp.getId(), experienceId))
                .findFirst()
                .orElse(null);
        if (exists == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        trip.getExperiences().remove(experience);
        tripService.save(trip);
        return new ResponseEntity<>(experienceId, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a trip")
    @DeleteMapping("/trips/{tripId}")
    public ResponseEntity<Long> delete(@PathVariable("tripId") Long id) {

        if (tripService.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        tripService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }
}

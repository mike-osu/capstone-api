package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.TripService;
import edu.oregonstate.capstone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    @Autowired
    TripService tripService;

    @Autowired
    UserService userService;

    @ApiOperation(value = "Get all trips")
    @GetMapping("/trips/all")
    public ResponseEntity<List<Trip>> getAll() {

        List<Trip> trips = tripService.getAll();
        return new ResponseEntity<>(trips, HttpStatus.OK);
    }

    @ApiOperation(value = "Get a trip by id")
    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> get(@PathVariable("id") Long id) {
        Trip trip = tripService.findById(id);

        if (trip == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trip, HttpStatus.OK);
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
}

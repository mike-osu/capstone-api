package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Trip;

import java.util.List;

public interface TripService {

    Trip findById(Long id);

    List<Trip> getAll();

    List<Trip> findByUserId(Long userId);

    Trip save(Trip trip);

    void delete(Long id);
}

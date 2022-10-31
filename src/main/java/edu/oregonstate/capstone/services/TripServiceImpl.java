package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    TripRepository tripRepository;

    @Override
    public Trip findById(Long id) {
        if (tripRepository.findById(id).isPresent()) {
            return tripRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Trip> getAll() {
        List<Trip> trips = tripRepository.findAll();
        trips.sort(Comparator.comparingLong(Trip::getId));
        return trips;
    }

    @Override
    public List<Trip> findByUserId(Long userId) {
        return tripRepository.findByUserId(userId);
    }

    @Override
    public Trip save(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public void delete(Long id) {
        tripRepository.deleteById(id);
    }
}

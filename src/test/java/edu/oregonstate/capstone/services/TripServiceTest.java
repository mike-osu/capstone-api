package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.repositories.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    TripRepository tripRepository;

    @InjectMocks
    TripServiceImpl tripService;

    private Trip trip;

    @BeforeEach
    void setUp() {
        tripService = new TripServiceImpl();
        ReflectionTestUtils.setField(tripService, "tripRepository", tripRepository);

        trip = new Trip();
        trip.setName("test trip");
        tripRepository.save(trip);
    }

    @Test
    void getAll() {
        tripService.getAll();
        verify(tripRepository).findAll();
    }

    @Test
    void findById() {
        Mockito.when(tripRepository.findById(trip.getId())).thenReturn(Optional.ofNullable(trip));
        assertEquals(tripService.findById(trip.getId()), trip);
    }

    @Test
    void save() {
        verify(tripRepository).save(trip);
    }
}

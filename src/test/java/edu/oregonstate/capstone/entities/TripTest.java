package edu.oregonstate.capstone.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    Trip trip;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setId(8675309L);
        trip.setName("test trip");
        trip.setDescription("description");
    }

    @Test
    void getId() {
        assertEquals(8675309L, trip.getId());
    }

    @Test
    void getTitle() {
        assertEquals("description", trip.getDescription());
    }
}

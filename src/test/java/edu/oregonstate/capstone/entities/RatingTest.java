package edu.oregonstate.capstone.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingTest {

    Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating();
        rating.setExperienceId(1L);
        rating.setUserId(1L);
        rating.setStarCount(5);
    }

    @Test
    void getIds() {
        assertEquals(1L, rating.getExperienceId());
        assertEquals(1L, rating.getUserId());
    }

    @Test
    void getStarCount() {
        assertEquals(5, rating.getStarCount());
    }
}

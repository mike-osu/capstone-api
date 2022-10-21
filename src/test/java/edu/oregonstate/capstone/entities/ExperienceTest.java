package edu.oregonstate.capstone.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperienceTest {

    Experience experience;

    @BeforeEach
    public void setUp() {
        experience = new Experience();
        experience.setId(1L);
        experience.setTitle("tour");
    }

    @Test
    void getId() {
        assertEquals(1L, experience.getId());
    }

    @Test
    void getTitle() {
        assertEquals("tour", experience.getTitle());
    }
}
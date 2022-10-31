package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.repositories.ExperienceRepository;
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

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ExperienceServiceTest {

    @Mock
    ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    private Experience exp1;
    private Experience exp2;
    private Trip trip;

    @BeforeEach void setUp() {
        experienceService = new ExperienceServiceImpl();
        ReflectionTestUtils.setField(experienceService, "experienceRepository", experienceRepository);

        exp1 = new Experience();
        exp2 = new Experience();
        trip = new Trip();
        trip.getExperiences().addAll(Arrays.asList(exp1, exp2));
    }

    @Test
    void getAll() {
        experienceService.getAll();
        verify(experienceRepository).findAll();
    }

    @Test
    void findByTripId() {
        experienceRepository.saveAll(Arrays.asList(exp1, exp2));
        Mockito.when(experienceRepository.findByTripsId(trip.getId())).thenReturn(Arrays.asList(exp1, exp2));
        assertEquals(experienceService.findByTripId(trip.getId()), Arrays.asList(exp1, exp2));
    }
}

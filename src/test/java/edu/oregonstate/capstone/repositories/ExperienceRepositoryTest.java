package edu.oregonstate.capstone.repositories;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Trip;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ExperienceRepositoryTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private TripRepository tripRepository;

    private Trip trip1, trip2;

    @BeforeEach
    public void setUp() {
        Experience exp1 = new Experience();
        exp1.setTitle("Camden Town");
        Experience exp2 = new Experience();
        exp2.setTitle("Basilica de la Sagrada Familia");
        experienceRepository.saveAll(Arrays.asList(exp1, exp2));

        trip1 = new Trip();
        trip1.setName("Trip to London");
        trip1.getExperiences().add(exp1);
        tripRepository.save(trip1);

        trip2 = new Trip();
        trip2.setName("European Vacation");
        trip2.setExperiences(new HashSet<>(Arrays.asList(exp1, exp2)));
        tripRepository.save(trip2);
    }

    @AfterEach
    public void tearDown() {
        trip1 = null;
        trip2 = null;
        tripRepository.deleteAll();
        experienceRepository.deleteAll();
    }

    @Test
    public void findByTripId() {
        List<Experience> actual = experienceRepository.findByTripsId(trip2.getId());
        assertEquals(2, actual.size());
        assertEquals("Camden Town", actual.get(0).getTitle());
        assertEquals("Basilica de la Sagrada Familia", actual.get(1).getTitle());
    }
}

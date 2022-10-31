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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TripRepositoryTest {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    private Experience exp1, exp2;

    @BeforeEach
    public void setUp() {
        Trip trip = new Trip();
        trip.setName("test trip");
        trip.setDescription("test description");

        exp1 = new Experience();
        exp1.setTitle("first exp");
        exp1.setDescription("test desc1");
        experienceRepository.save(exp1);

        exp2 = new Experience();
        exp2.setTitle("second exp");
        exp2.setDescription("test desc2");
        experienceRepository.save(exp2);

        trip.getExperiences().add(exp1);
        trip.getExperiences().add(exp2);
        tripRepository.save(trip);
    }

    @AfterEach
    public void tearDown() {
        exp1 = null;
        exp2 = null;
        experienceRepository.deleteAll();
        tripRepository.deleteAll();
    }

    @Test
    public void findByExperienceId() {
        List<Trip> actual = tripRepository.findByExperiencesId(exp2.getId());
        assertEquals(1, actual.size());
        assertEquals("test trip", actual.get(0).getName());

        actual = tripRepository.findByExperiencesId(3L);
        assertEquals(0, actual.size());
    }
}

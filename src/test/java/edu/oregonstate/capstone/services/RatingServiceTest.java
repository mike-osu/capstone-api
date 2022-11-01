package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.entities.Rating;
import edu.oregonstate.capstone.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @BeforeEach
    void setUp() {
        ratingService = new RatingServiceImpl();
        ReflectionTestUtils.setField(ratingService, "ratingRepository", ratingRepository);
        Rating rating = new Rating();
        rating.setExperienceId(50L);
        rating.setUserId(100L);
        rating.setStarCount(4);
        ratingService.save(rating);
    }

    @Test
    void save() {
        Rating rating = new Rating();
        rating.setExperienceId(2L);
        rating.setUserId(2L);
        rating.setStarCount(5);
        ratingService.save(rating);
        verify(ratingRepository).save(rating);
    }

    @Test
    void get() {
        Rating rating = ratingService.get(100L, 50L);
        verify(ratingRepository).findByUserIdAndExperienceId(100L, 50L);
    }

    @Test
    void deleteByExperienceId() {
        ratingService.deleteByExperienceId(50L);
        verify(ratingRepository).findAll();
    }
}

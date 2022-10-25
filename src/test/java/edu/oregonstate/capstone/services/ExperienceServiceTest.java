package edu.oregonstate.capstone.services;

import edu.oregonstate.capstone.repositories.ExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ExperienceServiceTest {

    @Mock
    ExperienceRepository experienceRepository;

    @InjectMocks
    private ExperienceServiceImpl experienceService;

    @BeforeEach void setUp() {
        experienceService = new ExperienceServiceImpl();
        ReflectionTestUtils.setField(experienceService, "experienceRepository", experienceRepository);
    }

    @Test
    void getAll() {
        experienceService.getAll();
        verify(experienceRepository).findAll();
    }
}

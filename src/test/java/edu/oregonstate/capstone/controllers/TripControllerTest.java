package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.TripService;
import edu.oregonstate.capstone.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TripController.class)
public class TripControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private UserService userService;

    @MockBean
    private ExperienceService experienceService;

    private Trip mockTrip;
    private User mockUser;

    String expected = "{\"id\":1,\"name\":\"Trip to Hawaii\",\"description\":\"Aloha!\"}";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();

        mockTrip = new Trip();
        mockTrip.setId(1L);
        mockTrip.setName("Trip to Hawaii");
        mockTrip.setDescription("Aloha!");

        mockUser = new User();
        mockUser.setId(100L);
        mockUser.setUsername("joeblow");
        mockUser.setEmail("joe@blow.com");
    }

    @Test
    public void getTrip() throws Exception {
        Mockito.when(tripService.findById(Mockito.anyLong())).thenReturn(mockTrip);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/trips/{tripId}", 1L)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    public void createTrip() throws Exception {
        Mockito.when(tripService.save(Mockito.any(Trip.class))).thenReturn(mockTrip);
        Mockito.when((userService.findById(Mockito.anyLong()))).thenReturn(mockUser);

        String tripJson = "{\"name\":\"Trip to Hawaii\",\"description\":\"Aloha!\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/users/{userId}/trips", 100L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(tripJson);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        assertEquals(expected, response.getContentAsString());
    }
}

package edu.oregonstate.capstone.bootstrap;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final ExperienceService experienceService;

    public DataLoader(UserService userService, ExperienceService experienceService) {
        this.userService = userService;
        this.experienceService = experienceService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = userService.getAll().size();

        if (count == 0 )
            loadData();
    }

    public void loadData() {
        User mike = new User();
        mike.setEmail("acostmic@oregonstate.edu");
        mike.setUsername("mike");
        userService.save(mike);

        Experience hawaii = new Experience();
        hawaii.setTitle("Aloha!");
        hawaii.setDescription("Trip to Hawaii");
        hawaii.setCity("Kaanapali");
        hawaii.setState("HI");
        hawaii.setUser(mike);
        experienceService.save(hawaii);

        System.out.println("Data loaded....");
    }
}

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

        User joeblow = new User();
        joeblow.setEmail("joe@blow.com");
        joeblow.setUsername("joeblow");
        userService.save(joeblow);

        System.out.println("Users loaded....");

        Experience hawaii = new Experience();
        hawaii.setTitle("Aloha!");
        hawaii.setDescription("Trip to Hawaii");
        hawaii.setCity("Kaanapali");
        hawaii.setState("Hawaii");
        hawaii.setCountry("United States");
        hawaii.setUser(mike);
        experienceService.save(hawaii);

        Experience europe = new Experience();
        europe.setTitle("European Adventure");
        europe.setDescription("Paris France, Brussels Belgium, Barcelona Spain, Amsterdam Netherlands, Munich Germany");
        europe.setCity("London");
        europe.setCountry("United Kingdom");
        europe.setUser(mike);
        experienceService.save(europe);

        Experience africa = new Experience();
        africa.setTitle("Trip to Africa");
        africa.setDescription("Africa abounds with incredible sights, from soaring sand dunes to savannas teeming with wildlife. Experience the many wonders on trips to Tanzania, South Africa, Botswana, Rwanda, Morocco, and more. Witness the annual wildebeest migration on safari in the Serengeti, or explore the sun-washed cities of Morocco through your camera lens. In South Africa, spot lions, elephants, giraffes, and more on a thrilling family safari. On many of our Africa expeditions, we arrange visits to National Geographic–sponsored research sites and talks with scientists in the field.");
        africa.setCity("Serengeti");
        africa.setCountry("Tanzania");
        africa.setUser(joeblow);
        experienceService.save(africa);

        System.out.println("Experiences loaded....");
    }
}

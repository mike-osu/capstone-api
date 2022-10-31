package edu.oregonstate.capstone.bootstrap;

import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.entities.Trip;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.services.ExperienceService;
import edu.oregonstate.capstone.services.TripService;
import edu.oregonstate.capstone.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final ExperienceService experienceService;
    private final TripService tripService;

    public DataLoader(UserService userService, ExperienceService experienceService, TripService tripService) {
        this.userService = userService;
        this.experienceService = experienceService;
        this.tripService = tripService;
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

        Experience exp1 = new Experience();
        exp1.setTitle("Amsterdam Open Boat Canal Cruise - Live Guide - from Anne Frank House");
        exp1.setDescription("Experience the beauty of Amsterdam’s canals by going on this scenic cruise. Pass by Anne Frank House, the Jordaan, the Houseboat Museum, Leiden Square, Rijksmuseum, De Duif and much more.");
        exp1.setCity("Amsterdam");
        exp1.setCountry("Netherlands");
        exp1.setUser(mike);
        experienceService.save(exp1);

        Experience exp2 = new Experience();
        exp2.setTitle("Dubai: Red Dunes Quad Bike, Sandsurf, Camels & BBQ at Al Khayma Camp");
        exp2.setDescription("Experience several desert pursuits in one outing, including ATV-driving—something many tours only offer at an extra cost—on this red-dune desert tour from Dubai. Skip the hassle of transport and logistical planning; and be free to simply enjoy the dunes and activities provided. Zoom off on an ATV, ride a camel, go sandboarding; enjoy henna art and Arabian-costume photos; and conclude with a barbecue-buffet dinner and live shows.");
        exp2.setCity("Dubai");
        exp2.setCountry("United Arab Emirates");
        exp2.setUser(mike);
        experienceService.save(exp2);

        Experience exp3 = new Experience();
        exp3.setTitle("Tour of North Shore and Sightseeing");
        exp3.setDescription("Skip the hassle of renting a car and see the highlights of Oahu’s North Shore from the comfort of a minibus or van. Along the way, a guide keeps you entertained and shares details about the island that you would likely miss if traveling on your own. At each stop, you can enjoy free time to swim, shop, paddleboard/kayak or do an optional waterfall hike while getting to know the island.");
        exp3.setCity("Haleiwa");
        exp3.setState("Hawaii");
        exp3.setCountry("United States");
        exp3.setUser(joeblow);
        experienceService.save(exp3);

        System.out.println("Experiences loaded....");

        Trip trip1 = new Trip();
        trip1.setName("Trip to Hawaii");
        trip1.setDescription("Aloha!");
        trip1.getExperiences().add(exp3);
        trip1.setUser(mike);
        tripService.save(trip1);

        System.out.println("Trips loaded....");
    }
}

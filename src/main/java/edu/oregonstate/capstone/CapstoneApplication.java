package edu.oregonstate.capstone;

import edu.oregonstate.capstone.aws.SqsListener;
import edu.oregonstate.capstone.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
public class CapstoneApplication {

    public static void main(String[] args) {

        ApplicationContext app = SpringApplication.run(CapstoneApplication.class, args);
        ExperienceService experienceService = app.getBean(ExperienceService.class);

        Executor executor = Executors.newSingleThreadExecutor();
        SqsListener runnable = new SqsListener(experienceService);
        executor.execute(runnable);
    }
}

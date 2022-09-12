package edu.oregonstate.capstone.configuration;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoConfig {

    @Bean
    public AWSCognitoIdentityProvider cognitoClient() {

        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }
}

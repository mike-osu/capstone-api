package edu.oregonstate.capstone.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Capstone API",
                "Spring Boot REST API Documentation",
                "1.0",
                "https://en.wikipedia.org/wiki/Terms_of_service",
                new Contact("Oregon State",
                        "https://eecs.oregonstate.edu/academic/online-cs-postbacc",
                        "acostmic@oregonstate.edu"),
                "License",
                "https://en.wikipedia.org/wiki/Software_license",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.oregonstate.capstone.controllers"))
                .paths(PathSelectors.any())
                .build();
    }
}

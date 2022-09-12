package edu.oregonstate.capstone.controllers;

import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.models.LoginRequest;
import edu.oregonstate.capstone.models.SignupRequest;
import edu.oregonstate.capstone.models.Secret;
import edu.oregonstate.capstone.security.SecretManager;
import edu.oregonstate.capstone.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    private final String cognitoUrl = "https://cognito-idp.us-west-2.amazonaws.com/us-west-2_Xef2qmwBv";

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<User> getByUsername(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        HttpStatus httpStatus = HttpStatus.OK;
        String loginResponse;

        try {
            Secret secret = SecretManager.getSecret();

            JSONObject authParams = new JSONObject();
            authParams.put("USERNAME", loginRequest.getUsername());
            authParams.put("PASSWORD", loginRequest.getPassword());

            JSONObject body = new JSONObject();
            body.put("AuthParameters", authParams);
            body.put("ClientId", secret.getAppClientId());
            body.put("AuthFlow", "USER_PASSWORD_AUTH");

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-amz-json-1.1");
            headers.set("X-Amz-Target", "AWSCognitoIdentityProviderService.InitiateAuth");
            HttpEntity<String> entity = new HttpEntity<>(body.toString(), headers);
            loginResponse = restTemplate.postForObject(cognitoUrl, entity, String.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loginResponse = e.getMessage();
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(loginResponse, httpStatus);
    }

    @PostMapping("/users/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

        HttpStatus httpStatus = HttpStatus.OK;
        String signupResponse;

        try {
            Secret secret = SecretManager.getSecret();

            JSONArray userAttributes = new JSONArray();
            JSONObject emailAttribute = new JSONObject();
            emailAttribute.put("Name", "email");
            emailAttribute.put("Value", signupRequest.getEmail());
            userAttributes.put(emailAttribute);

            JSONObject body = new JSONObject();
            body.put("UserAttributes", userAttributes);
            body.put("ClientId", secret.getAppClientId());
            body.put("Username", signupRequest.getUsername());
            body.put("Password", signupRequest.getPassword());

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-amz-json-1.1");
            headers.set("X-Amz-Target", "AWSCognitoIdentityProviderService.SignUp");
            signupResponse = restTemplate.postForObject(cognitoUrl,
                    new HttpEntity<>(body.toString(), headers),
                    String.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            signupResponse = e.getMessage();
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(signupResponse, httpStatus);
    }
}

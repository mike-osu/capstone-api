package edu.oregonstate.capstone.controllers;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.oregonstate.capstone.entities.User;
import edu.oregonstate.capstone.models.LoginRequest;
import edu.oregonstate.capstone.models.Secret;
import edu.oregonstate.capstone.security.SecretManager;
import edu.oregonstate.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws JsonProcessingException {

        String loginResponse;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            Secret secret = SecretManager.getSecret();

            AdminInitiateAuthRequest request = new AdminInitiateAuthRequest()
                    .withUserPoolId(secret.getUserPoolId())
                    .withClientId(secret.getAppClientId())
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .withAuthParameters(getAuthParams(loginRequest));

            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(request);
            loginResponse = result.getAuthenticationResult().getIdToken();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            loginResponse = e.getMessage();
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return new ResponseEntity<>(loginResponse, httpStatus);
    }

    private Map<String, String> getAuthParams(LoginRequest loginRequest) {
        Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", loginRequest.getUsername());
        authParams.put("PASSWORD", loginRequest.getPassword());
        return authParams;
    }
}

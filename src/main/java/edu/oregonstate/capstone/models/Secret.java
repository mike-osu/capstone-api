package edu.oregonstate.capstone.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Secret {

    private String accessKey;

    private String secretKey;

    private String userPoolId;

    private String appClientId;

    public String getAccessKey() {
        return accessKey;
    }

    @JsonProperty("aws-access-key-id")
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    @JsonProperty("aws-secret-key")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    @JsonProperty("user-pool-id")
    public void setUserPoolId(String userPoolId) {
        this.userPoolId = userPoolId;
    }

    public String getAppClientId() {
        return appClientId;
    }

    @JsonProperty("app-client-id")
    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }
}

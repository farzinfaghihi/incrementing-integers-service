package com.farzinfaghihi.thinkific.v1;

public class AuthenticationResponse {

    private final String apiKey;

    public AuthenticationResponse(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }
}

package com.automation.helpers;

import com.automation.models.LoginRequest;
import com.automation.models.LoginResponse;
import com.automation.modules.UserService;
import io.restassured.response.Response;

public class TokenManager {
    private static TokenManager instance;
    private String accessToken;

    private TokenManager() {
    }

    public static synchronized TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    public String getToken() {
        if (accessToken == null) {
            login();
        }
        return accessToken;
    }

    private void login() {
        UserService userService = new UserService();
        LoginRequest loginRequest = LoginRequest.builder()
                .email("sahabajuser@yopmail.com")
                .password("Password@800")
                .role("USER")
                .build();

        Response response = userService.login(loginRequest);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to login in TokenManager. Status Code: " + response.getStatusCode());
        }

        LoginResponse loginResponse = response.as(LoginResponse.class);
        if (loginResponse.getData() != null && loginResponse.getData().getToken() != null) {
            this.accessToken = loginResponse.getData().getToken().getAccess();
        } else {
            throw new RuntimeException("Login successful but token not found in response.");
        }
    }
}

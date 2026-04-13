package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.context.TestContext;
import com.automation.models.LoginRequest;
import com.automation.models.LoginResponse;
import com.automation.modules.UserService;
import com.automation.utils.JsonDataReader;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {

    /**
     * DataProvider that reads login credentials from JSON file
     * 
     * @return Object[][] containing LoginRequest objects
     */
    @DataProvider(name = "loginUsersData")
    public Object[][] getLoginUsers() {
        // Read JSON file and convert to List of LoginRequest objects
        List<LoginRequest> loginUsers = JsonDataReader.readJsonAsList(
                "testdata/login_users.json",
                new TypeReference<List<LoginRequest>>() {
                });

        // Convert List to Object[][] for TestNG DataProvider
        Object[][] data = new Object[loginUsers.size()][1];
        for (int i = 0; i < loginUsers.size(); i++) {
            data[i][0] = loginUsers.get(i);
        }
        return data;
    }

    /**
     * Data-driven login test that executes for each user from JSON file
     * Extracts and stores user IDs in TestContext for use in subsequent tests
     * 
     * @param loginRequest - Login credentials from JSON data
     */
    @Test(dataProvider = "loginUsersData", description = "Login test for multiple users from JSON")
    public void testBulkUserLogin(LoginRequest loginRequest) {
        UserService userService = new UserService();

        System.out.println("Testing login for user: " + loginRequest.getEmail());

        Response response = userService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200,
                "Login failed for user: " + loginRequest.getEmail());

        LoginResponse loginResponse = response.as(LoginResponse.class);

        if (loginResponse.getData() != null && loginResponse.getData().getToken() != null) {
            String accessToken = loginResponse.getData().getToken().getAccess();
            String userId = loginResponse.getData().getUser().getId();

            System.out.println("✓ Login successful for: " + loginRequest.getEmail());
            System.out.println("  User ID: " + userId);
            System.out
                    .println("  Access Token: " + accessToken.substring(0, Math.min(50, accessToken.length())) + "...");

            // Store user ID and token in TestContext for use in next tests
            TestContext.getInstance().addUserId(userId);
            TestContext.getInstance().addAccessToken(accessToken);

            Assert.assertNotNull(accessToken, "Access token should not be null for user: " + loginRequest.getEmail());
            Assert.assertNotNull(userId, "User ID should not be null for user: " + loginRequest.getEmail());
        } else {
            Assert.fail("Login response data or token is null for user: " + loginRequest.getEmail());
        }
    }

    /**
     * Single user login test (kept for backward compatibility)
     */
    @Test
    public void testLoginAndExtractToken() {
        UserService userService = new UserService();

        LoginRequest loginRequest = LoginRequest.builder()
                .email("sahabajuser@yopmail.com")
                .password("Password@800")
                .role("USER")
                .build();

        Response response = userService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200);

        LoginResponse loginResponse = response.as(LoginResponse.class);

        if (loginResponse.getData() != null && loginResponse.getData().getToken() != null) {
            String accessToken = loginResponse.getData().getToken().getAccess();
            System.out.println("Extracted Access Token: " + accessToken);
            Assert.assertNotNull(accessToken, "Access token should not be null");

            // Initializing/Storing token for subsequent requests if implementing that logic
            // For now, identifying how to access it is the primary goal.
        } else {
            Assert.fail("Login response data or token is null");
        }
    }
}

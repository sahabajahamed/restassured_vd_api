package com.automation.tests;

import com.automation.base.BaseTest;

import com.automation.models.ProfileResponse;
import com.automation.modules.UserService;
import com.automation.helpers.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetProfileTest extends BaseTest {

    @Test
    public void testGetProfile() {
        UserService userService = new UserService();

        // 1. Get valid token from TokenManager and set it
        String accessToken = TokenManager.getInstance().getToken();
        System.setProperty("access.token", accessToken);

        // 2. Get Profile
        Response profileResponseRaw = userService.getProfile();
        Assert.assertEquals(profileResponseRaw.getStatusCode(), 200, "Get Profile failed");

        ProfileResponse profileResponse = profileResponseRaw.as(ProfileResponse.class);
        Assert.assertEquals(profileResponse.getCode(), 200);
        Assert.assertEquals(profileResponse.getMessage(), "Profile fetched successfully");
        Assert.assertNotNull(profileResponse.getData(), "Profile data is null");
        Assert.assertNotNull(profileResponse.getData().getId(), "Profile ID is null");
        Assert.assertNotNull(profileResponse.getData().getFullName(), "Profile Name is null");

        System.out.println("Profile verified for user: " + profileResponse.getData().getFullName());
    }
}

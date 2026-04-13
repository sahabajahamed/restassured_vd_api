package com.automation.tests;

import com.automation.base.BaseTest;

import com.automation.models.ProfileRequest;
import com.automation.modules.UserService;
import com.automation.helpers.TokenManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditProfileTest extends BaseTest {

    @Test
    public void testEditProfile() {
        UserService userService = new UserService();

        // 1. Get valid token from TokenManager and set it
        String accessToken = TokenManager.getInstance().getToken();
        System.setProperty("access.token", accessToken);

        // 2. Edit Profile
        ProfileRequest profileRequest = ProfileRequest.builder()
                .fullName("Sahabaj Ahammed Rest Assured")
                .profileImage("https://example.com/new-profile.jpg")
                .build();

        Response editResponseRaw = userService.editProfile(profileRequest);
        Assert.assertEquals(editResponseRaw.getStatusCode(), 200, "Edit Profile failed");

        // Verify response message
        String message = editResponseRaw.jsonPath().getString("message");
        Assert.assertEquals(message, "Profile updated successfully");

        // 3. Verify changes with Get Profile
        Response getProfileResponseRaw = userService.getProfile();
        Assert.assertEquals(getProfileResponseRaw.getStatusCode(), 200, "Get Profile failed");

        String updatedName = getProfileResponseRaw.jsonPath().getString("data.fullName");
        Assert.assertEquals(updatedName, "Sahabaj Ahammed Rest Assured", "Profile name not updated");

        System.out.println("Profile updated and verified: " + updatedName);
    }
}

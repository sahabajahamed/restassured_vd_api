package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.models.User;
import com.automation.modules.UserService;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests extends BaseTest {

    private UserService userService;
    private Faker faker;
    private User userPayload;
    private int userId;

    @BeforeClass
    public void setupData() {
        userService = new UserService();
        faker = new Faker();

        userPayload = User.builder()
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .gender("male")
                .status("active")
                .build();
    }

    @Test(priority = 1)
    public void testCreateUser() {
        Response response = userService.createUser(userPayload);

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), userPayload.getName());

        // Capture ID for subsequent tests
        userId = response.jsonPath().getInt("id");
    }

}

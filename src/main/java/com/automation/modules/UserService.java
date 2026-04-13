package com.automation.modules;

import com.automation.constants.EndPoints;
import com.automation.helpers.RequestBuilder;
import com.automation.models.User;
import com.automation.reports.ExtentReportManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public Response login(com.automation.models.LoginRequest loginRequest) {
        logger.info("Logging in with email: " + loginRequest.getEmail());
        ExtentReportManager.getTest().info("Login Request: " + loginRequest.toString());

        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .body(loginRequest)
                .post(EndPoints.LOGIN);

        logResponse(response);
        return response;
    }

    public Response getProfile() {
        logger.info("Getting user profile");
        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .get(EndPoints.GET_PROFILE);
        logResponse(response);
        return response;
    }

    public Response editProfile(com.automation.models.ProfileRequest profileRequest) {
        logger.info("Editing user profile: " + profileRequest.getFullName());
        ExtentReportManager.getTest().info("Edit Profile Request: " + profileRequest.toString());

        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .body(profileRequest)
                .post(EndPoints.EDIT_PROFILE);

        logResponse(response);
        return response;
    }

    public Response createUser(User user) {
        logger.info("Creating user: " + user.getName());
        ExtentReportManager.getTest().info("Creating user payload: " + user.toString());

        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .body(user)
                .post(EndPoints.USERS);

        logResponse(response);
        return response;
    }

    public Response getUser(int id) {
        logger.info("Getting user with ID: " + id);
        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .get(EndPoints.USERS + "/" + id);
        logResponse(response);
        return response;
    }

    public Response updateUser(int id, User user) {
        logger.info("Updating user with ID: " + id);
        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .body(user)
                .put(EndPoints.USERS + "/" + id);
        logResponse(response);
        return response;
    }

    public Response deleteUser(int id) {
        logger.info("Deleting user with ID: " + id);
        Response response = RestAssured.given()
                .spec(RequestBuilder.getBaseRequestSpec())
                .delete(EndPoints.USERS + "/" + id);
        logResponse(response);
        return response;
    }

    private void logResponse(Response response) {
        ExtentReportManager.getTest().info("Response Status: " + response.getStatusCode());
        ExtentReportManager.getTest().info("Response Body: " + response.getBody().asPrettyString());
        logger.info("Response Status: " + response.getStatusCode());
    }
}

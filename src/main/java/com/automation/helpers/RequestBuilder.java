package com.automation.helpers;

import com.automation.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestBuilder {

    public static RequestSpecification getBaseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getInstance().getString("base.url"))
                .addHeader("Authorization", "Bearer " + ConfigManager.getInstance().getString("access.token"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }
}

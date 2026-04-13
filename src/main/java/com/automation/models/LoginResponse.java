package com.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private int code;
    private String message;
    private DataContent data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataContent {
        private User user;
        private Token token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)

    public static class User {
        private String id;
        private String fullName;
        private String email;
        private String phone;
        private String createdAt;
        private boolean isBlocked;
        private boolean isVerified;
        private String providerId;
        private String provider;
        private String profileImage;
        private String stripeCustomerId;
        private String lat;
        private String lng;
        private String roleId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Role {
        private int id;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private int id;
        private String country;
        private String state;
        private String city;
        private String zipCode;
        private String lat;
        private String lng;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Token {
        private String access;
        private String refresh;
    }
}

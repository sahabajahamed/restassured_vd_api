package com.automation.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Shared context to store data across test methods and test classes
 * Thread-safe implementation for parallel test execution
 */
public class TestContext {

    private static TestContext instance;

    // Thread-safe list to store user IDs from login responses
    private List<String> userIds;

    // Thread-safe list to store access tokens
    private List<String> accessTokens;

    private TestContext() {
        this.userIds = new CopyOnWriteArrayList<>();
        this.accessTokens = new CopyOnWriteArrayList<>();
    }

    /**
     * Get singleton instance of TestContext
     * 
     * @return TestContext instance
     */
    public static synchronized TestContext getInstance() {
        if (instance == null) {
            instance = new TestContext();
        }
        return instance;
    }

    /**
     * Add a user ID to the context
     * 
     * @param userId - User ID to store
     */
    public void addUserId(String userId) {
        if (userId != null && !userId.isEmpty()) {
            userIds.add(userId);
            System.out.println("✓ Stored User ID: " + userId + " (Total: " + userIds.size() + ")");
        }
    }

    /**
     * Add an access token to the context
     * 
     * @param token - Access token to store
     */
    public void addAccessToken(String token) {
        if (token != null && !token.isEmpty()) {
            accessTokens.add(token);
        }
    }

    /**
     * Get all stored user IDs
     * 
     * @return Unmodifiable list of user IDs
     */
    public List<String> getUserIds() {
        return Collections.unmodifiableList(new ArrayList<>(userIds));
    }

    /**
     * Get all stored access tokens
     * 
     * @return Unmodifiable list of access tokens
     */
    public List<String> getAccessTokens() {
        return Collections.unmodifiableList(new ArrayList<>(accessTokens));
    }

    /**
     * Get user ID at specific index
     * 
     * @param index - Index position
     * @return User ID at the specified index
     */
    public String getUserIdAt(int index) {
        if (index >= 0 && index < userIds.size()) {
            return userIds.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index + ", size: " + userIds.size());
    }

    /**
     * Get access token at specific index
     * 
     * @param index - Index position
     * @return Access token at the specified index
     */
    public String getAccessTokenAt(int index) {
        if (index >= 0 && index < accessTokens.size()) {
            return accessTokens.get(index);
        }
        throw new IndexOutOfBoundsException("Invalid index: " + index + ", size: " + accessTokens.size());
    }

    /**
     * Get count of stored user IDs
     * 
     * @return Number of user IDs
     */
    public int getUserIdsCount() {
        return userIds.size();
    }

    /**
     * Get count of stored access tokens
     * 
     * @return Number of access tokens
     */
    public int getAccessTokensCount() {
        return accessTokens.size();
    }

    /**
     * Clear all stored user IDs
     */
    public void clearUserIds() {
        userIds.clear();
        System.out.println("✓ Cleared all user IDs from context");
    }

    /**
     * Clear all stored access tokens
     */
    public void clearAccessTokens() {
        accessTokens.clear();
        System.out.println("✓ Cleared all access tokens from context");
    }

    /**
     * Clear all data from context
     */
    public void clearAll() {
        userIds.clear();
        accessTokens.clear();
        System.out.println("✓ Cleared all data from context");
    }

    /**
     * Print all stored user IDs
     */
    public void printUserIds() {
        System.out.println("\n========== Stored User IDs ==========");
        for (int i = 0; i < userIds.size(); i++) {
            System.out.println((i + 1) + ". " + userIds.get(i));
        }
        System.out.println("Total: " + userIds.size() + " user IDs");
        System.out.println("=====================================\n");
    }
}

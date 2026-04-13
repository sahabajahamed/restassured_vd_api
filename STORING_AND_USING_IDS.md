# How to Store and Use User IDs Across Tests

## Overview
This guide explains how to extract user IDs from login responses and use them in subsequent test scripts.

## Components Created

### 1. TestContext Class
**Location:** `src/main/java/com/automation/context/TestContext.java`

A singleton class that stores and manages data across different test scripts.

**Key Methods:**
```java
// Store data
TestContext.getInstance().addUserId(userId);
TestContext.getInstance().addAccessToken(token);

// Retrieve data
List<String> userIds = TestContext.getInstance().getUserIds();
String userId = TestContext.getInstance().getUserIdAt(0);

// Get counts
int count = TestContext.getInstance().getUserIdsCount();

// Clear data
TestContext.getInstance().clearAll();

// Print all IDs
TestContext.getInstance().printUserIds();
```

### 2. Updated LoginTest
**Location:** `src/test/java/com/automation/tests/LoginTest.java`

The `testBulkUserLogin` method now:
1. Logs in 10 users from JSON file
2. Extracts user ID from each login response
3. Stores user ID and access token in TestContext

### 3. Example Usage Test
**Location:** `src/test/java/com/automation/tests/UseStoredUserIdsTest.java`

Demonstrates 4 different ways to use stored user IDs:
- **Data Provider Pattern**: Loop through all stored IDs
- **Batch Operations**: Process all IDs in a single test
- **Index Access**: Get specific IDs by position
- **Print All**: Display all stored IDs

## How It Works

### Step 1: Login and Store IDs
```java
@Test(dataProvider = "loginUsersData")
public void testBulkUserLogin(LoginRequest loginRequest) {
    // Login user
    Response response = userService.login(loginRequest);
    LoginResponse loginResponse = response.as(LoginResponse.class);
    
    // Extract user ID from response
    String userId = loginResponse.getData().getUser().getId();
    String accessToken = loginResponse.getData().getToken().getAccess();
    
    // Store in TestContext
    TestContext.getInstance().addUserId(userId);
    TestContext.getInstance().addAccessToken(accessToken);
}
```

### Step 2: Use Stored IDs in Next Script

#### Option 1: Data Provider Pattern (Run test for each ID)
```java
@DataProvider(name = "storedUserIds")
public Object[][] getStoredUserIds() {
    List<String> userIds = TestContext.getInstance().getUserIds();
    
    Object[][] data = new Object[userIds.size()][1];
    for (int i = 0; i < userIds.size(); i++) {
        data[i][0] = userIds.get(i);
    }
    return data;
}

@Test(dataProvider = "storedUserIds")
public void testWithEachUserId(String userId) {
    // This test will run once for each stored user ID
    System.out.println("Testing with User ID: " + userId);
    // Your test logic here
}
```

#### Option 2: Get All IDs at Once
```java
@Test
public void testWithAllUserIds() {
    List<String> userIds = TestContext.getInstance().getUserIds();
    
    for (String userId : userIds) {
        // Process each user ID
        System.out.println("Processing: " + userId);
    }
}
```

#### Option 3: Get Specific ID by Index
```java
@Test
public void testWithFirstUser() {
    String firstUserId = TestContext.getInstance().getUserIdAt(0);
    String firstToken = TestContext.getInstance().getAccessTokenAt(0);
    
    // Use the first user's ID and token
}
```

## Example: Update Profile for Each Logged-in User

```java
package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.context.TestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;

public class UpdateProfileTest extends BaseTest {

    @DataProvider(name = "storedUserData")
    public Object[][] getStoredUserData() {
        TestContext context = TestContext.getInstance();
        List<String> userIds = context.getUserIds();
        List<String> tokens = context.getAccessTokens();
        
        Object[][] data = new Object[userIds.size()][2];
        for (int i = 0; i < userIds.size(); i++) {
            data[i][0] = userIds.get(i);
            data[i][1] = tokens.get(i);
        }
        return data;
    }

    @Test(dataProvider = "storedUserData")
    public void testUpdateProfileForEachUser(String userId, String accessToken) {
        System.out.println("Updating profile for User ID: " + userId);
        
        // Your API call to update profile
        // Use userId and accessToken from the parameters
        
        // Example:
        // Response response = given()
        //     .header("Authorization", "Bearer " + accessToken)
        //     .body(updateRequest)
        //     .when()
        //     .put("/v1/profile/edit-profile")
        //     .then()
        //     .extract().response();
    }
}
```

## Test Execution Order

To ensure TestContext has data, configure execution order in `testng.xml`:

```xml
<suite name="API Test Suite">
    <test name="Login and Store Data">
        <classes>
            <class name="com.automation.tests.LoginTest">
                <methods>
                    <include name="testBulkUserLogin"/>
                </methods>
            </class>
        </classes>
    </test>
    
    <test name="Use Stored Data">
        <classes>
            <class name="com.automation.tests.UseStoredUserIdsTest"/>
            <class name="com.automation.tests.UpdateProfileTest"/>
        </classes>
    </test>
</suite>
```

## Key Benefits

1. ✅ **No Duplicate Logins**: Login once, use data multiple times
2. ✅ **Data Sharing**: Share data across different test classes
3. ✅ **Thread-Safe**: Uses CopyOnWriteArrayList for parallel execution
4. ✅ **Flexible Access**: Get all IDs, specific ID, or iterate through them
5. ✅ **Clean API**: Simple, intuitive methods

## Important Notes

⚠️ **Test Order**: Tests that use stored data must run AFTER LoginTest

⚠️ **Cleanup**: Call `TestContext.getInstance().clearAll()` in teardown if needed

⚠️ **Null Checks**: Always check if data exists before using it

```java
if (TestContext.getInstance().getUserIdsCount() > 0) {
    // Safe to use stored IDs
}
```

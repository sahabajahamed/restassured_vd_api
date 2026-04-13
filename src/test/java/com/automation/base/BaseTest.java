package com.automation.base;

import com.automation.reports.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.lang.reflect.Method;

public class BaseTest {

    @BeforeSuite
    public void setUp(ITestContext context) {
        ExtentReportManager.initReports();
    }

    @BeforeMethod
    public void registerTest(Method method) {
        ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void tearDownTest() {
        ExtentReportManager.flushReports();
    }

    @AfterSuite
    public void tearDown() {
        ExtentReportManager.flushReports();
    }
}

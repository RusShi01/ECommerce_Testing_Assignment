package com.automation.listeners;

import com.automation.listeners.TestUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        try {
            WebDriver driver = (WebDriver) testClass.getClass().getField("driver").get(testClass);
            String testName = result.getMethod().getMethodName();
            TestUtils.captureScreenshot(driver, testName);
            TestUtils.logToReport(" Test Failed: " + testName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestUtils.logToReport(" Test Skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        TestUtils.logToReport("Completed Suite: " + context.getName());
    }
}

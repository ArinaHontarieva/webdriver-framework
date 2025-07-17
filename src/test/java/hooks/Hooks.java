package hooks;

import factory.DriverFactory;
import helpers.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.LoggerUtil;
import utils.ScreenshotUtil;

public class Hooks {

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    @BeforeMethod
    public void setup() {
        String browser = new PropertiesHelper().getBrowser();
        DriverFactory.getInstance().initializeDriver(browser);
        getDriver().manage().window().maximize();
        getDriver().get(new PropertiesHelper().getBaseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getMethod().getMethodName();
            String screenshotPath = ScreenshotUtil.takeScreenshot(getDriver(), testName);
            LoggerUtil.LOG.error("Test failed: " + testName);
            LoggerUtil.LOG.error("Screenshot saved at: " + screenshotPath);
        } else {
            LoggerUtil.LOG.info("Test passed: " + result.getMethod().getMethodName());
        }
        DriverFactory.getInstance().closeDriver();
    }
}

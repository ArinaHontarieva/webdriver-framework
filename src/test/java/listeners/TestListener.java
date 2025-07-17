package listeners;

import hooks.Hooks;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((Hooks) testClass).getDriver();

        String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getName());
        System.out.println("🧷 Screenshot saved to: " + screenshotPath);
    }
}

package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String path = "screenshots/" + testName + "_" + timestamp + ".png";
        try {
            FileUtils.copyFile(source, new File(path));
            return path;
        } catch (IOException e) {
            LoggerUtil.LOG.error("Failed to save screenshot", e);
            return null;
        }
    }
}


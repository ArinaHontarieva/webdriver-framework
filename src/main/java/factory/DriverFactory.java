package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverFactory {

    private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static DriverFactory instance;

    private DriverFactory() {}

    public static synchronized DriverFactory getInstance() {
        if (instance == null) {
            instance = new DriverFactory();
        }
        return instance;
    }

    public WebDriver initializeDriver(String browser) {
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                tlDriver.set(new ChromeDriver());
                System.out.println("Chrome WebDriver initialized successfully.");
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                tlDriver.set(new FirefoxDriver());
                System.out.println("Firefox WebDriver initialized successfully.");
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                tlDriver.set(new EdgeDriver());
                System.out.println("Edge WebDriver initialized successfully.");
            }
            default -> throw new RuntimeException("Driver is not initialized");
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public void closeDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
    private final Properties props = new Properties();

    public PropertiesHelper() {
        try {
            String env = System.getProperty("env", "prod");
            String path = env + ".properties";
            InputStream input = getClass().getClassLoader().getResourceAsStream(path);
            if (input == null) {
                throw new RuntimeException("Config file not found: " + path);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }

    public String getBrowser() {
        return props.getProperty("browser");
    }

    public String getBaseUrl() {
        return props.getProperty("base.url");
    }
}

package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    private static final String PROPERTIES_FILE = "src/main/resources/config.properties";
    private static Properties properties;

    public PropertiesHelper() {
        properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(PROPERTIES_FILE);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
}

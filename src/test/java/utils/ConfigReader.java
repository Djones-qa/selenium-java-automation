package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_PATH);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "true"));
    }

    public static String getStandardUser() {
        return properties.getProperty("standard.user");
    }

    public static String getLockedUser() {
        return properties.getProperty("locked.user");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }

    public static String getReportPath() {
        return properties.getProperty("report.path", "reports/ExtentReport.html");
    }

    public static String getReportTitle() {
        return properties.getProperty("report.title", "Automation Report");
    }

    public static String getReportName() {
        return properties.getProperty("report.name", "Test Results");
    }

    public static String getScreenshotDir() {
        return properties.getProperty("screenshot.dir", "reports/screenshots/");
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(properties.getProperty("screenshot.on.failure", "true"));
    }
}

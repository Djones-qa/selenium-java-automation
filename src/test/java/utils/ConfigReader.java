package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (in == null) throw new RuntimeException("config.properties not found on classpath");
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getBaseUrl()       { return props.getProperty("base.url"); }
    public static String getBrowser()       { return props.getProperty("browser", "chrome"); }
    public static String getStandardUser()  { return props.getProperty("standard.user"); }
    public static String getLockedUser()    { return props.getProperty("locked.user"); }
    public static String getPassword()      { return props.getProperty("password"); }
    public static boolean isHeadless()      { return Boolean.parseBoolean(props.getProperty("headless", "true")); }
    public static int getExplicitWait()     { return Integer.parseInt(props.getProperty("explicit.wait", "10")); }
    public static String getReportPath()    { return props.getProperty("report.path", "reports/ExtentReport.html"); }
    public static String getReportTitle()   { return props.getProperty("report.title", "Test Report"); }
    public static String getReportName()    { return props.getProperty("report.name", "Test Results"); }
}

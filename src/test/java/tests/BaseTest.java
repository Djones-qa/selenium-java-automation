package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.LogUtils;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    protected WebDriver driver;

    static {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        LogUtils.info("Setting up browser for thread: " + Thread.currentThread().getId());
        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.isHeadless()) {
            options.addArguments("--headless");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        driverThreadLocal.set(new ChromeDriver(options));
        driver = driverThreadLocal.get();
        LogUtils.info("Browser launched on thread: " + Thread.currentThread().getId());
    }

    @AfterMethod
    public void tearDown() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
            LogUtils.info("Browser closed on thread: " + Thread.currentThread().getId());
        }
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }
}

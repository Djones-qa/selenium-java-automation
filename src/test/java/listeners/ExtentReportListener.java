package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ConfigReader;
import utils.ScreenshotUtils;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(ConfigReader.getReportPath());
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setDocumentTitle(ConfigReader.getReportTitle());
        reporter.config().setReportName(ConfigReader.getReportName());
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Darrius Jones");
        extent.setSystemInfo("Environment", ConfigReader.getBaseUrl());
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        extentTest.info("Test started: " + result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());

        Object testInstance = result.getInstance();
        WebDriver driver = null;

        try {
            driver = (WebDriver) testInstance.getClass().getDeclaredField("driver").get(testInstance);
        } catch (Exception e) {
            test.get().log(Status.WARNING, "Could not get driver for screenshot: " + e.getMessage());
        }

        if (driver != null) {
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                try {
                    test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                    test.get().log(Status.INFO, "Screenshot captured: " + screenshotPath);
                } catch (Exception e) {
                    test.get().log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        System.out.println("Report: " + ConfigReader.getReportPath());
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}

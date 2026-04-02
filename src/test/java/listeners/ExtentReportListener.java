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
import utils.LogUtils;
import utils.ScreenshotUtils;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Object lock = new Object();

    private static void initExtent() {
        synchronized (lock) {
            if (extent == null) {
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
                LogUtils.info("ExtentReports initialized");
            }
        }
    }

    @Override
    public void onStart(ITestContext context) {
        initExtent();
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.startTest(result.getMethod().getMethodName());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        extentTest.info("Test started: " + result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.pass(result.getMethod().getMethodName());
        test.get().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.fail(result.getMethod().getMethodName());
        LogUtils.error("Failure reason: " + result.getThrowable().getMessage());
        test.get().log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());

        try {
            WebDriver driver = tests.BaseTest.getDriver();
            if (driver != null && ConfigReader.isScreenshotOnFailure()) {
                String path = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
                if (path != null) {
                    test.get().addScreenCaptureFromPath(path, "Failure Screenshot");
                    LogUtils.info("Screenshot attached: " + path);
                }
            }
        } catch (Exception e) {
            LogUtils.error("Could not capture screenshot", e);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test skipped: " + result.getMethod().getMethodName());
        test.get().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        LogUtils.info("Report: " + ConfigReader.getReportPath());
        LogUtils.info("Passed: " + context.getPassedTests().size()
                + " | Failed: " + context.getFailedTests().size()
                + " | Skipped: " + context.getSkippedTests().size());
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}

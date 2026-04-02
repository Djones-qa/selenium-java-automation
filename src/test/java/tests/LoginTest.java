package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import utils.ConfigReader;
import utils.LogUtils;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "Valid login redirects to inventory page")
    public void testValidLogin() {
        LogUtils.startTest("testValidLogin");
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        LogUtils.info("Navigating to: " + ConfigReader.getBaseUrl());
        loginPage.navigate();
        LogUtils.info("Logging in as: " + ConfigReader.getStandardUser());
        loginPage.login(ConfigReader.getStandardUser(), ConfigReader.getPassword());
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Should be on inventory page");
        LogUtils.pass("testValidLogin");
        LogUtils.endTest("testValidLogin");
    }

    @Test(description = "Invalid login shows error message")
    public void testInvalidLogin() {
        LogUtils.startTest("testInvalidLogin");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        LogUtils.info("Attempting login with invalid credentials");
        loginPage.login("invalid_user", "wrong_pass");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        LogUtils.pass("testInvalidLogin");
        LogUtils.endTest("testInvalidLogin");
    }

    @Test(description = "Locked out user sees error message")
    public void testLockedOutUser() {
        LogUtils.startTest("testLockedOutUser");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        LogUtils.info("Attempting login with locked out user");
        loginPage.login(ConfigReader.getLockedUser(), ConfigReader.getPassword());
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
        LogUtils.pass("testLockedOutUser");
        LogUtils.endTest("testLockedOutUser");
    }

    @Test(description = "Empty credentials shows error")
    public void testEmptyCredentials() {
        LogUtils.startTest("testEmptyCredentials");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        LogUtils.info("Clicking login with empty credentials");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        LogUtils.pass("testEmptyCredentials");
        LogUtils.endTest("testEmptyCredentials");
    }
}

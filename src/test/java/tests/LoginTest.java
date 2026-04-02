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
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = new InventoryPage(getDriver());
        loginPage.navigate();
        loginPage.loginAndWaitForInventory(ConfigReader.getStandardUser(), ConfigReader.getPassword());
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Should be on inventory page");
        LogUtils.pass("testValidLogin");
    }

    @Test(description = "Invalid login shows error message")
    public void testInvalidLogin() {
        LogUtils.startTest("testInvalidLogin");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigate();
        loginPage.login("invalid_user", "wrong_pass");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        LogUtils.pass("testInvalidLogin");
    }

    @Test(description = "Locked out user sees error message")
    public void testLockedOutUser() {
        LogUtils.startTest("testLockedOutUser");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigate();
        loginPage.login(ConfigReader.getLockedUser(), ConfigReader.getPassword());
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
        LogUtils.pass("testLockedOutUser");
    }

    @Test(description = "Empty credentials shows error")
    public void testEmptyCredentials() {
        LogUtils.startTest("testEmptyCredentials");
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.navigate();
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        LogUtils.pass("testEmptyCredentials");
    }
}

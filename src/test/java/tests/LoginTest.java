package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "Valid login redirects to inventory page")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        loginPage.navigate();
        loginPage.loginAndWaitForInventory(ConfigReader.getStandardUser(), ConfigReader.getPassword());
        Assert.assertTrue(inventoryPage.isOnInventoryPage(), "Should be on inventory page");
    }

    @Test(description = "Invalid login shows error message")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login("invalid_user", "wrong_pass");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
    }

    @Test(description = "Locked out user sees error message")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.login(ConfigReader.getLockedUser(), ConfigReader.getPassword());
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"), "Should show locked out message");
    }

    @Test(description = "Empty credentials shows error")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
    }
}

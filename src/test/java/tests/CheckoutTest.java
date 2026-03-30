package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.InventoryPage;
import pages.CheckoutPage;

public class CheckoutTest extends BaseTest {

    private void loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigate();
        loginPage.loginAndWaitForInventory("standard_user", "secret_sauce");
    }

    @Test(groups = {"smoke"}, description = "Add product to cart updates cart count")
    public void testAddToCart() {
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartCount(), "1", "Cart count should be 1");
    }

    @Test(description = "Add multiple products updates cart count")
    public void testAddMultipleToCart() {
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        inventoryPage.addToCart("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartCount(), "2", "Cart count should be 2");
    }

    @Test(groups = {"smoke"}, description = "Complete full checkout journey")
    public void testFullCheckout() {
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.proceedToCheckout();
        checkoutPage.fillShippingInfo("Darrius", "Jones", "44870");
        checkoutPage.continueCheckout();
        checkoutPage.finishOrder();
        Assert.assertTrue(checkoutPage.isOrderConfirmed(), "Order should be confirmed");
    }

    @Test(description = "Sort products by price low to high")
    public void testSortByPriceLowToHigh() {
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        inventoryPage.sortBy("lohi");
        java.util.List<Double> prices = inventoryPage.getProductPrices();
        java.util.List<Double> sorted = new java.util.ArrayList<>(prices);
        java.util.Collections.sort(sorted);
        Assert.assertEquals(prices, sorted, "Products should be sorted by price ascending");
    }

    @Test(description = "Sort products by name A to Z")
    public void testSortByNameAtoZ() {
        loginAsStandardUser();
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.waitForPageLoad();
        inventoryPage.sortBy("az");
        java.util.List<String> names = inventoryPage.getProductNames();
        java.util.List<String> sorted = new java.util.ArrayList<>(names);
        java.util.Collections.sort(sorted);
        Assert.assertEquals(names, sorted, "Products should be sorted alphabetically");
    }
}

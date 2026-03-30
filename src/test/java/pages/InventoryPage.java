package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    private final By inventoryListLocator = By.className("inventory_list");
    private final By cartBadgeLocator = By.className("shopping_cart_badge");
    private final By cartLinkLocator = By.className("shopping_cart_link");
    private final By sortDropdownLocator = By.className("product_sort_container");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryListLocator));
    }

    public boolean isOnInventoryPage() {
        waitForPageLoad();
        return driver.getCurrentUrl().contains("inventory");
    }

    public void addToCart(String productName) {
        String dataTest = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[data-test='" + dataTest + "']")
        ));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", btn);
        // wait for the button to change to "Remove" confirming the add registered
        wait.until(ExpectedConditions.presenceOfElementLocated(cartBadgeLocator));
    }

    public String getCartCount() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(cartBadgeLocator)).getText();
    }

    public void goToCart() {
        driver.get("https://www.saucedemo.com/cart.html");
    }

    public void sortBy(String option) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(sortDropdownLocator));
        new Select(dropdown).selectByValue(option);
    }

    public List<String> getProductNames() {
        waitForPageLoad();
        return driver.findElements(By.className("inventory_item_name"))
            .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        waitForPageLoad();
        return driver.findElements(By.className("inventory_item_price"))
            .stream()
            .map(e -> Double.parseDouble(e.getText().replace("$", "")))
            .collect(Collectors.toList());
    }
}

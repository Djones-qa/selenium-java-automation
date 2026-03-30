package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutPage extends BasePage {

    private final By firstNameLocator  = By.id("first-name");
    private final By lastNameLocator   = By.id("last-name");
    private final By postalCodeLocator = By.id("postal-code");
    private final By continueLocator   = By.id("continue");
    private final By finishLocator     = By.cssSelector("[data-test='finish']");
    private final By checkoutLocator   = By.id("checkout");
    private final By confirmLocator    = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void proceedToCheckout() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(checkoutLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(d -> d.getCurrentUrl().contains("checkout-step-one"));
    }

    public void fillShippingInfo(String firstName, String lastName, String zip) {
        setInputValue(firstNameLocator, firstName);
        setInputValue(lastNameLocator, lastName);
        setInputValue(postalCodeLocator, zip);
    }

    private void setInputValue(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        // Use JS to set value and fire React's synthetic change event
        ((JavascriptExecutor) driver).executeScript(
            "var el = arguments[0]; var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set; nativeInputValueSetter.call(el, arguments[1]); el.dispatchEvent(new Event('input', { bubbles: true }));",
            el, value
        );
    }

    public void continueCheckout() {
        // continue is an <input type="submit">, use JS click
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(continueLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        new WebDriverWait(driver, Duration.ofSeconds(20))
            .until(d -> d.getCurrentUrl().contains("checkout-step-two"));
    }

    public void finishOrder() {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(finishLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isOrderConfirmed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

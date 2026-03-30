# Selenium Java Automation Framework

Automated UI test suite for [SauceDemo](https://www.saucedemo.com) built with Selenium WebDriver, TestNG, and Java 17.

## Tech Stack

- Java 17
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- WebDriverManager 5.7.0
- Maven

## Project Structure

```
src/test/java/
├── pages/
│   ├── BasePage.java        # Shared WebDriver helpers and explicit waits
│   ├── LoginPage.java       # Login page interactions
│   ├── InventoryPage.java   # Product listing, add to cart, sort
│   └── CheckoutPage.java    # Cart and checkout flow
└── tests/
    ├── BaseTest.java        # ChromeDriver setup/teardown
    ├── LoginTest.java       # Login test cases
    └── CheckoutTest.java    # Cart and checkout test cases
```

## Test Cases

**Login Tests**
- Valid login redirects to inventory page
- Invalid credentials shows error message
- Locked out user sees error message
- Empty credentials shows error

**Checkout Tests**
- Add product to cart updates cart count
- Add multiple products updates cart count
- Complete full checkout journey (end-to-end)
- Sort products by price low to high
- Sort products by name A to Z

## Running the Tests

```bash
mvn clean test
```

Tests run headless by default. Results are output to `target/surefire-reports/`.

## Prerequisites

- Java 17+
- Maven 3.6+
- Google Chrome installed (WebDriverManager handles the driver automatically)

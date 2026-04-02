# Selenium Java Automation Framework

![CI](https://github.com/Djones-qa/selenium-java-automation/actions/workflows/test.yml/badge.svg)

UI test automation framework for [SauceDemo](https://www.saucedemo.com) built with Selenium WebDriver, TestNG, Java 17, ExtentReports, Log4j2, and configuration management.

## Tech Stack

- Java 17
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- WebDriverManager 5.7.0
- ExtentReports 5.1.1
- Log4j2 2.23.1
- Maven
- GitHub Actions CI

## Project Structure

```
src/test/java/
├── pages/
│   ├── BasePage.java              # Shared WebDriver helpers and explicit waits
│   ├── LoginPage.java             # Login page interactions
│   ├── InventoryPage.java         # Product listing, add to cart, sort
│   └── CheckoutPage.java          # Cart and checkout flow
├── tests/
│   ├── BaseTest.java              # ChromeDriver setup/teardown via ConfigReader
│   ├── LoginTest.java             # Login test cases
│   └── CheckoutTest.java          # Cart and checkout test cases
├── listeners/
│   └── ExtentReportListener.java  # TestNG listener — HTML report + failure screenshots
└── utils/
    ├── ConfigReader.java          # Loads config.properties
    ├── ScreenshotUtils.java       # Captures PNG on test failure
    └── LogUtils.java              # Log4j2 wrapper for structured logging

src/test/resources/
├── config.properties              # Externalized configuration
├── log4j2.xml                     # Log4j2 configuration
└── testng.xml                     # TestNG suite definition
```

## Configuration

All settings live in `src/test/resources/config.properties`:

```properties
base.url=https://www.saucedemo.com
browser=chrome
headless=true
standard.user=standard_user
password=secret_sauce
explicit.wait=10
report.path=reports/ExtentReport.html
```

## Logging

Log4j2 outputs to both console and `reports/automation.log`. Each test logs:
- Browser setup and teardown
- Navigation and actions
- Pass/fail result with clear markers

## Test Coverage (9 tests)

### Login Tests
- Valid login redirects to inventory page
- Invalid credentials shows error message
- Locked out user sees error message
- Empty credentials shows error

### Checkout Tests
- Add product to cart updates cart count
- Add multiple products updates cart count
- Complete full checkout journey (end-to-end)
- Sort products by price low to high
- Sort products by name A to Z

## Reporting

After each run, an HTML report is generated at `reports/ExtentReport.html` with:
- Pass/fail status per test
- Failure screenshots automatically captured and embedded
- System info (OS, browser, environment)

## Running the Tests

```bash
mvn clean test
```

## Prerequisites

- Java 17+
- Maven 3.6+
- Google Chrome (WebDriverManager handles the driver automatically)

## Author

Darrius Jones — [github.com/Djones-qa](https://github.com/Djones-qa)

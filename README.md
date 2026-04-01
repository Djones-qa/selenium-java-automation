# Selenium Java Automation Framework

![CI](https://github.com/Djones-qa/selenium-java-automation/actions/workflows/test.yml/badge.svg)

Enterprise-grade UI test automation framework for SauceDemo built with Selenium WebDriver, TestNG, Java 17, ExtentReports, and configuration management.

## Tech Stack
- Java 17
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- WebDriverManager 5.7.0
- ExtentReports 5.1.1
- Maven
- GitHub Actions CI

## Project Structure
`
selenium-java-automation/
├── src/test/java/
│   ├── pages/
│   │   ├── BasePage.java          # Shared WebDriver helpers and explicit waits
│   │   ├── LoginPage.java         # Login page interactions
│   │   ├── InventoryPage.java     # Product listing, add to cart, sort
│   │   └── CheckoutPage.java      # Cart and checkout flow
│   ├── tests/
│   │   ├── BaseTest.java          # ChromeDriver setup/teardown with ConfigReader
│   │   ├── LoginTest.java         # Login test cases
│   │   └── CheckoutTest.java      # Cart and checkout test cases
│   ├── listeners/
│   │   └── ExtentReportListener.java  # TestNG listener for HTML reporting
│   └── utils/
│       └── ConfigReader.java      # Properties file configuration manager
├── src/test/resources/
│   ├── config.properties          # Externalized test configuration
│   └── testng.xml                 # TestNG suite configuration
├── reports/
│   └── ExtentReport.html          # Generated HTML test report
└── .github/workflows/
    └── selenium-tests.yml         # GitHub Actions CI pipeline
`

## Configuration Management
All test configuration externalized in config.properties:
- Base URL
- Browser and headless mode
- Test credentials
- Timeouts
- Report settings

## Test Coverage (9 tests)

### Login Tests (4 tests)
- Valid login redirects to inventory page
- Invalid credentials shows error message
- Locked out user sees error message
- Empty credentials shows error

### Checkout Tests (5 tests)
- Add product to cart updates cart count
- Add multiple products updates cart count
- Complete full checkout journey end to end
- Sort products by price low to high
- Sort products by name A to Z

## Run Tests
`ash
mvn test
`

Tests run headless by default. ExtentReport generated at reports/ExtentReport.html

## Author
Darrius Jones - github.com/Djones-qa

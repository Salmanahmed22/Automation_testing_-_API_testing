# TutorialsNinja Automation Framework

## Tech Stack
- Java 11
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- Allure Report 2.26.0
- Apache POI 5.2.5
- Maven

## Project Structure
- base/        → DriverManager, BasePage, BaseTest
- pages/       → Page Object classes
- tests/       → TestNG test classes
- utils/       → ConfigReader, ExcelReader, ScreenshotUtil

## How to Run Tests
mvn clean test

## How to Generate Allure Report
allure serve target/allure-results

## Configuration
Edit src/test/resources/config.properties to change browser or URL.

## Test Data
Edit src/test/resources/testdata/TestData.xlsx to change test inputs.

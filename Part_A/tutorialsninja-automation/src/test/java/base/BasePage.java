package base;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        int explicitWait = Integer.parseInt(ConfigReader.get("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    protected void waitAndClick(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void waitAndType(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected void waitAndClickWithRetry(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                attempts++;
            }
        }
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }

    protected void hoverAndClick(By hoverTarget, By clickTarget) {
        WebElement target = wait.until(ExpectedConditions.visibilityOfElementLocated(hoverTarget));
        new Actions(driver).moveToElement(target).perform();
        WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(clickTarget));
        new Actions(driver).moveToElement(link).click().perform();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

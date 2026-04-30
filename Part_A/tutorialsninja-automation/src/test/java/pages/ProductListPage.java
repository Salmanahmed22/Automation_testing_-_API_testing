package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProductListPage extends BasePage {

    private static final By BREADCRUMB_LAST  = By.cssSelector("#product-category li:last-child a, .breadcrumb li:last-child a");
    private static final By LEFT_MENU_ACTIVE = By.cssSelector("#column-left .list-group-item.active");
    private static final By SORT_DROPDOWN    = By.id("input-sort");
    private static final By PRODUCT_NAMES    = By.cssSelector(".product-thumb h4 a");
    private static final By PRODUCT_PRICES   = By.cssSelector(".product-thumb .price");
    private static final By ADD_TO_CART_BUTTONS = By.cssSelector(".product-thumb .btn-primary");
    private static final By SUCCESS_TOAST    = By.cssSelector(".alert-success");

    public ProductListPage(WebDriver driver) {
        super(driver);
    }

    public String getBreadcrumbLastItem() {
        return getText(BREADCRUMB_LAST);
    }

    public String getLeftMenuActiveItem() {
        return getText(LEFT_MENU_ACTIVE);
    }

    public void sortBy(String visibleText) {
        new Select(driver.findElement(SORT_DROPDOWN)).selectByVisibleText(visibleText);
    }

    public List<String> getProductNames() {
        return driver.findElements(PRODUCT_NAMES).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getProductPrices() {
        return driver.findElements(PRODUCT_PRICES).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void addToCartByIndex(int index) {
        driver.findElements(ADD_TO_CART_BUTTONS).get(index).click();
    }

    // Waits for the success alert to be visible — replaces Thread.sleep after add-to-cart
    public boolean isSuccessToastDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TOAST));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addToCartByName(String productName) {
        List<WebElement> thumbs = driver.findElements(By.cssSelector(".product-thumb"));
        for (WebElement thumb : thumbs) {
            String name = thumb.findElement(By.cssSelector("h4 a")).getText();
            if (name.equals(productName)) {
                ((org.openqa.selenium.JavascriptExecutor) driver)
                        .executeScript("arguments[0].scrollIntoView(true);", thumb);
                List<WebElement> buttons = thumb.findElements(By.cssSelector(".btn-primary"));
                if (!buttons.isEmpty()) {
                    wait.until(ExpectedConditions.elementToBeClickable(buttons.get(0))).click();
                } else {
                    thumb.findElement(By.cssSelector("h4 a")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();
                }
                return;
            }
        }
        throw new RuntimeException("Product not found on page: " + productName);
    }
}

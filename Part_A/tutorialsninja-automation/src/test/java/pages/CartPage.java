package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {

    private static final By CART_ITEMS = By.cssSelector("#content table tbody tr");
    private static final By PRODUCT_NAME_IN_CART = By.cssSelector("#content table tbody tr td:nth-child(2) a");
    private static final By PRODUCT_PRICE_IN_CART = By.cssSelector("#content table tbody tr td:nth-child(5)");
    // Totals live in a separate table inside .col-sm-offset-8, not in a tfoot of the products table
    private static final By TOTAL_PRICE = By.cssSelector("#content .col-sm-offset-8 table tr:last-child td:last-child");
    private static final By SUB_TOTAL   = By.cssSelector("#content .col-sm-offset-8 table tr:first-child td:last-child");
    private static final By CHECKOUT_BUTTON = By.linkText("Checkout");
    private static final By VIEW_CART_BUTTON = By.linkText("View Cart");
    private static final By DATE_INPUT = By.cssSelector("input[id^='option']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getCartProductNames() {
        return driver.findElements(PRODUCT_NAME_IN_CART).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public boolean isProductInCart(String productName) {
        return getCartProductNames().stream().anyMatch(n -> n.contains(productName));
    }

    public String getTotalPrice() {
        return getText(TOTAL_PRICE);
    }

    public String getSubTotal() {
        return getText(SUB_TOTAL);
    }

    public void clickCheckout() {
        waitAndClick(CHECKOUT_BUTTON);
    }

    public void setDeliveryDate(String date) {
        waitAndType(DATE_INPUT, date);
    }

    public List<String> getProductPricesInCart() {
        return driver.findElements(PRODUCT_PRICE_IN_CART).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}

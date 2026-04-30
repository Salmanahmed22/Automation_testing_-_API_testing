package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    private static final By BILLING_NEW_ADDRESS_RADIO = By.cssSelector("input[value='new']");
    private static final By BILLING_FIRSTNAME  = By.id("input-payment-firstname");
    private static final By BILLING_LASTNAME   = By.id("input-payment-lastname");
    private static final By BILLING_ADDRESS1   = By.id("input-payment-address-1");
    private static final By BILLING_CITY       = By.id("input-payment-city");
    private static final By BILLING_POSTCODE   = By.id("input-payment-postcode");
    private static final By BILLING_COUNTRY    = By.id("input-payment-country");
    private static final By BILLING_ZONE       = By.id("input-payment-zone");
    private static final By BILLING_CONTINUE   = By.id("button-payment-address");
    private static final By SHIPPING_NEW_ADDRESS_RADIO = By.cssSelector("#collapse-shipping-address input[value='new']");
    private static final By SHIPPING_CONTINUE  = By.id("button-shipping-address");
    private static final By DELIVERY_CONTINUE  = By.id("button-shipping-method");
    private static final By DELIVERY_COMMENT   = By.cssSelector("textarea[name='comment']");
    private static final By TERMS_CHECKBOX     = By.name("agree");
    private static final By PAYMENT_CONTINUE   = By.id("button-payment-method");
    private static final By CONFIRM_ORDER_BUTTON = By.id("button-confirm");
    private static final By SUCCESS_MESSAGE    = By.cssSelector("#content h1");
    private static final By ORDER_TOTAL        = By.cssSelector("#checkout-total tfoot tr:last-child td:last-child");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillBillingDetails(String firstName, String lastName, String address,
                                   String city, String postcode, String country, String zone) {
        if (isDisplayed(BILLING_NEW_ADDRESS_RADIO)) {
            waitAndClick(BILLING_NEW_ADDRESS_RADIO);
        }
        waitAndType(BILLING_FIRSTNAME, firstName);
        waitAndType(BILLING_LASTNAME, lastName);
        waitAndType(BILLING_ADDRESS1, address);
        waitAndType(BILLING_CITY, city);
        waitAndType(BILLING_POSTCODE, postcode);

        new Select(driver.findElement(BILLING_COUNTRY)).selectByVisibleText(country);

        // Zone options are populated via AJAX after country selection — wait for them to load
        wait.until(d -> new Select(d.findElement(BILLING_ZONE)).getOptions().size() > 1);
        new Select(driver.findElement(BILLING_ZONE)).selectByVisibleText(zone);

        waitAndClick(BILLING_CONTINUE);
    }

    public void continueShipping() {
        waitAndClick(SHIPPING_CONTINUE);
    }

    public void continueDelivery(String comment) {
        waitAndType(DELIVERY_COMMENT, comment);
        waitAndClick(DELIVERY_CONTINUE);
    }

    public void agreeAndContinuePayment() {
        waitAndClick(TERMS_CHECKBOX);
        waitAndClick(PAYMENT_CONTINUE);
    }

    public void confirmOrder() {
        waitAndClick(CONFIRM_ORDER_BUTTON);
    }

    public String getSuccessMessage() {
        return getText(SUCCESS_MESSAGE);
    }

    public String getOrderTotal() {
        return getText(ORDER_TOTAL);
    }
}

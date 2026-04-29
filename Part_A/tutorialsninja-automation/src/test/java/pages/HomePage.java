package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class HomePage extends BasePage {

    private static final By MY_ACCOUNT_DROPDOWN = By.xpath("//span[text()='My Account']");
    private static final By REGISTER_LINK = By.linkText("Register");
    private static final By LOGIN_LINK = By.linkText("Login");
    private static final By LOGOUT_LINK = By.linkText("Logout");
    private static final By SEARCH_BOX = By.name("search");
    private static final By SEARCH_BUTTON = By.cssSelector("button.btn-default[type='submit']");
    private static final By CURRENCY_DROPDOWN = By.cssSelector("button.btn-link.dropdown-toggle span.hidden-xs.hidden-sm.hidden-md");
    private static final By EURO_OPTION = By.xpath("//button[@name='EUR']");
    private static final By DOLLAR_OPTION = By.xpath("//button[@name='USD']");
    private static final By NAV_DESKTOPS = By.xpath("//a[contains(@href,'desktop') and contains(@class,'dropdown-toggle')]");
    private static final By SHOW_ALL_DESKTOPS = By.xpath("//a[contains(@href,'route=product/category&path=20')]");
    private static final By NAV_TABLETS = By.linkText("Tablets");
    private static final By NAV_PHONES = By.xpath("//a[contains(@href,'route=product/category&path=24')]");
    private static final By NAV_MP3 = By.xpath("//a[contains(@href,'route=product/category&path=57') and contains(@class,'dropdown-toggle')]");
    private static final By SHOW_ALL_MP3 = By.xpath("//a[contains(@href,'route=product/category&path=57') and not(contains(@class,'dropdown-toggle'))]");
    private static final By SHOPPING_CART_BUTTON = By.cssSelector("#cart > button");
    private static final By CART_ITEM_COUNT = By.cssSelector("#cart > button > span.hidden-xs.hidden-sm.hidden-md");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToRegister() {
        waitAndClick(MY_ACCOUNT_DROPDOWN);
        waitAndClick(REGISTER_LINK);
    }

    public void goToLogin() {
        waitAndClick(MY_ACCOUNT_DROPDOWN);
        waitAndClick(LOGIN_LINK);
    }

    public void logout() {
        waitAndClick(MY_ACCOUNT_DROPDOWN);
        waitAndClick(LOGOUT_LINK);
    }

    public void searchFor(String keyword) {
        waitAndType(SEARCH_BOX, keyword);
        waitAndClick(SEARCH_BUTTON);
    }

    public void changeCurrencyToEuro() {
        waitAndClick(CURRENCY_DROPDOWN);
        waitAndClick(EURO_OPTION);
    }

    public void changeCurrencyToDollar() {
        waitAndClick(CURRENCY_DROPDOWN);
        waitAndClick(DOLLAR_OPTION);
    }

    public void goToDesktops() {
        new Actions(driver).moveToElement(driver.findElement(NAV_DESKTOPS)).perform();
        waitAndClick(SHOW_ALL_DESKTOPS);
    }

    public void goToTablets() {
        waitAndClick(NAV_TABLETS);
    }

    public void goToPhones() {
        waitAndClick(NAV_PHONES);
    }

    public void goToMP3Players() {
        new Actions(driver).moveToElement(driver.findElement(NAV_MP3)).perform();
        waitAndClick(SHOW_ALL_MP3);
    }

    public String getCartItemCount() {
        return getText(CART_ITEM_COUNT);
    }

    public void openCart() {
        waitAndClick(SHOPPING_CART_BUTTON);
    }
}

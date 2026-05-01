package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class HomePage extends BasePage {

    private static final By MY_ACCOUNT_DROPDOWN = By.xpath("//span[text()='My Account']");
    private static final By REGISTER_LINK = By.linkText("Register");
    private static final By LOGIN_LINK = By.linkText("Login");
    private static final By LOGOUT_LINK = By.linkText("Logout");
    private static final By SEARCH_BOX = By.name("search");
    private static final By SEARCH_BUTTON = By.cssSelector("div#search button");
    private static final By CURRENCY_DROPDOWN = By.cssSelector("button.btn-link.dropdown-toggle span.hidden-xs.hidden-sm.hidden-md");
    private static final By EURO_OPTION = By.xpath("//button[@name='EUR']");
    private static final By DOLLAR_OPTION = By.xpath("//button[@name='USD']");
    private static final By NAV_DESKTOPS = By.linkText("Desktops");
    private static final By NAV_DESKTOPS_SHOW_ALL = By.cssSelector("a.see-all[href*='path=20']");
    private static final By NAV_LAPTOPS = By.linkText("Laptops & Notebooks");
    private static final By NAV_LAPTOPS_SHOW_ALL = By.cssSelector("a.see-all[href*='path=18']");
    private static final By NAV_TABLETS = By.linkText("Tablets");
    private static final By NAV_PHONES = By.xpath("//a[contains(@href,'route=product/category&path=24')]");
    private static final By NAV_MP3 = By.linkText("MP3 Players");
    private static final By NAV_MP3_SHOW_ALL = By.cssSelector("a.see-all[href*='path=34']");
    private static final By VIEW_CART_LINK = By.linkText("View Cart");
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
        waitAndClickWithRetry(MY_ACCOUNT_DROPDOWN);
        waitAndClickWithRetry(LOGOUT_LINK);
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
        hoverAndClick(NAV_DESKTOPS, NAV_DESKTOPS_SHOW_ALL);
    }

    public void goToTablets() {
        waitAndClickWithRetry(NAV_TABLETS);
    }

    public void goToPhones() {
        waitAndClickWithRetry(NAV_PHONES);
    }

    public void goToMP3Players() {
        hoverAndClick(NAV_MP3, NAV_MP3_SHOW_ALL);
    }

    public void goToLaptops() {
        hoverAndClick(NAV_LAPTOPS, NAV_LAPTOPS_SHOW_ALL);
    }

    public void navigateToCategoryByName(String category) {
        switch (category.trim().toLowerCase()) {
            case "tablets":     goToTablets();    break;
            case "phones":
            case "phones & pdas": goToPhones();  break;
            case "desktops":    goToDesktops();   break;
            case "mp3 players": goToMP3Players(); break;
            case "laptops":
            case "laptops & notebooks": goToLaptops(); break;
            default: throw new IllegalArgumentException("Unknown category: " + category);
        }
    }

    public void clickViewCart() {
        waitAndClick(VIEW_CART_LINK);
    }

    public String getCartItemCount() {
        return getText(CART_ITEM_COUNT);
    }

    public void openCart() {
        waitAndClick(SHOPPING_CART_BUTTON);
    }
}

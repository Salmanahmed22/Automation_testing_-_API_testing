package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage extends BasePage {

    private static final By PAGE_HEADING = By.cssSelector("#content h2");
    private static final By LOGOUT_LINK_SIDEBAR = By.linkText("Logout");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountPageDisplayed() {
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(PAGE_HEADING));
            return getText(PAGE_HEADING).contains("My Account");
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageHeading() {
        return getText(PAGE_HEADING);
    }
}

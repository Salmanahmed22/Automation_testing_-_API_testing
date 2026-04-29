package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By EMAIL = By.id("input-email");
    private static final By PASSWORD = By.id("input-password");
    private static final By LOGIN_BUTTON = By.cssSelector("input[value='Login']");
    private static final By ERROR_MESSAGE = By.cssSelector(".alert.alert-danger.alert-dismissible");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        waitAndType(EMAIL, email);
        waitAndType(PASSWORD, password);
        waitAndClick(LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}

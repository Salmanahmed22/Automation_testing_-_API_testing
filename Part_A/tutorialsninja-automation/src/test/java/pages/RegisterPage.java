package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private static final By FIRST_NAME = By.id("input-firstname");
    private static final By LAST_NAME = By.id("input-lastname");
    private static final By EMAIL = By.id("input-email");
    private static final By TELEPHONE = By.id("input-telephone");
    private static final By PASSWORD = By.id("input-password");
    private static final By CONFIRM_PASSWORD = By.id("input-confirm");
    private static final By PRIVACY_POLICY_CHECKBOX = By.name("agree");
    private static final By CONTINUE_BUTTON = By.cssSelector("input[value='Continue']");
    private static final By SUCCESS_MESSAGE = By.cssSelector("#content h1");
    private static final By FIRSTNAME_ERROR = By.cssSelector("#account > div:nth-child(1) .text-danger");
    private static final By LASTNAME_ERROR = By.cssSelector("#account > div:nth-child(2) .text-danger");
    private static final By EMAIL_ERROR = By.cssSelector("#account > div:nth-child(3) .text-danger");
    private static final By TELEPHONE_ERROR = By.cssSelector("#account > div:nth-child(4) .text-danger");
    private static final By PASSWORD_ERROR = By.cssSelector("#account > div:nth-child(5) .text-danger");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void register(String firstName, String lastName, String email,
                         String telephone, String password, String confirmPassword) {
        waitAndType(FIRST_NAME, firstName);
        waitAndType(LAST_NAME, lastName);
        waitAndType(EMAIL, email);
        waitAndType(TELEPHONE, telephone);
        waitAndType(PASSWORD, password);
        waitAndType(CONFIRM_PASSWORD, confirmPassword);
        waitAndClick(PRIVACY_POLICY_CHECKBOX);
        waitAndClick(CONTINUE_BUTTON);
    }

    public void clickContinueWithoutFilling() {
        waitAndClick(CONTINUE_BUTTON);
    }

    public void fillPartial(String firstName, String lastName) {
        waitAndType(FIRST_NAME, firstName);
        waitAndType(LAST_NAME, lastName);
        waitAndClick(CONTINUE_BUTTON);
    }

    public void fillAllExceptPassword(String firstName, String lastName,
                                      String email, String telephone) {
        waitAndType(FIRST_NAME, firstName);
        waitAndType(LAST_NAME, lastName);
        waitAndType(EMAIL, email);
        waitAndType(TELEPHONE, telephone);
        waitAndClick(PRIVACY_POLICY_CHECKBOX);
        waitAndClick(CONTINUE_BUTTON);
    }

    public void fillWithShortPassword(String firstName, String lastName, String email,
                                      String telephone, String shortPassword) {
        waitAndType(FIRST_NAME, firstName);
        waitAndType(LAST_NAME, lastName);
        waitAndType(EMAIL, email);
        waitAndType(TELEPHONE, telephone);
        waitAndType(PASSWORD, shortPassword);
        waitAndType(CONFIRM_PASSWORD, shortPassword);
        waitAndClick(PRIVACY_POLICY_CHECKBOX);
        waitAndClick(CONTINUE_BUTTON);
    }

    public String getSuccessMessage() {
        return getText(SUCCESS_MESSAGE);
    }

    public boolean isFirstNameErrorDisplayed() {
        return isDisplayed(FIRSTNAME_ERROR);
    }

    public boolean isLastNameErrorDisplayed() {
        return isDisplayed(LASTNAME_ERROR);
    }

    public boolean isEmailErrorDisplayed() {
        return isDisplayed(EMAIL_ERROR);
    }

    public boolean isTelephoneErrorDisplayed() {
        return isDisplayed(TELEPHONE_ERROR);
    }

    public boolean isPasswordErrorDisplayed() {
        return isDisplayed(PASSWORD_ERROR);
    }

    public String getPasswordErrorText() {
        return getText(PASSWORD_ERROR);
    }
}

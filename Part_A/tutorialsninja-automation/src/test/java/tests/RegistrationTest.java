package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;
import utils.ExcelReader;

@Epic("User Management")
public class RegistrationTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return ExcelReader.getTestData("Registration");
    }

    @Test(dataProvider = "registrationData")
    @Story("Successful Registration")
    @Description("Register a new user with valid data and verify account creation")
    public void testSuccessfulRegistration(String firstName, String lastName, String email,
                                           String telephone, String password, String confirmPassword) {
        navigateToRegister();
        fillRegistrationForm(firstName, lastName, email, telephone, password, confirmPassword);
        verifySuccessMessage();
        verifyLogout();
    }

    @Step("Navigate to Register page")
    private void navigateToRegister() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToRegister();
    }

    @Step("Fill registration form with valid data")
    private void fillRegistrationForm(String firstName, String lastName, String email,
                                       String telephone, String password, String confirmPassword) {
        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.register(firstName, lastName, email, telephone, password, confirmPassword);
    }

    @Step("Verify success message")
    private void verifySuccessMessage() {
        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        String successMsg = registerPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your Account Has Been Created!"),
                "Expected success message not found. Actual: " + successMsg);
    }

    @Step("Verify logout option is available")
    private void verifyLogout() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.logout();
    }

    @Test
    @Story("Registration Validation Errors")
    @Description("Verify error messages appear when submitting empty registration form")
    public void testRegistrationWithErrors() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToRegister();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());
        registerPage.clickContinueWithoutFilling();

        Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(), "First name error not shown");
        Assert.assertTrue(registerPage.isLastNameErrorDisplayed(), "Last name error not shown");
        Assert.assertTrue(registerPage.isEmailErrorDisplayed(), "Email error not shown");
        Assert.assertTrue(registerPage.isTelephoneErrorDisplayed(), "Telephone error not shown");
        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(), "Password error not shown");

        registerPage.fillWithShortPassword("Test", "User",
                "shortpwdtest@test.com", "0501111111", "abc");

        String pwdError = registerPage.getPasswordErrorText();
        Assert.assertTrue(pwdError.contains("Password must be between 4 and 20 characters!"),
                "Password length error not shown. Actual: " + pwdError);
    }
}

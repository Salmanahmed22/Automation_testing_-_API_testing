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
@Feature("Registration")
public class RegistrationErrorsTest extends BaseTest {

    @DataProvider(name = "registrationErrorsData")
    public Object[][] registrationErrorsData() {
        return ExcelReader.getTestData("RegistrationErrors");
    }

    @Test(dataProvider = "registrationErrorsData")
    @Severity(SeverityLevel.NORMAL)
    @Story("Registration Validation Errors")
    @Description("Verify error messages appear when submitting empty form, and when password is too short")
    public void testRegistrationWithErrors(String firstName, String lastName,
                                           String email, String telephone, String shortPassword) {
        new HomePage(DriverManager.getDriver()).goToRegister();

        RegisterPage registerPage = new RegisterPage(DriverManager.getDriver());

        submitEmptyFormAndVerifyErrors(registerPage);
        submitShortPasswordAndVerifyError(registerPage, firstName, lastName, email, telephone, shortPassword);
    }

    @Step("Submit empty registration form and verify all field errors")
    private void submitEmptyFormAndVerifyErrors(RegisterPage registerPage) {
        registerPage.clickContinueWithoutFilling();
        Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(), "First name error not shown");
        Assert.assertTrue(registerPage.isLastNameErrorDisplayed(), "Last name error not shown");
        Assert.assertTrue(registerPage.isEmailErrorDisplayed(), "Email error not shown");
        Assert.assertTrue(registerPage.isTelephoneErrorDisplayed(), "Telephone error not shown");
        Assert.assertTrue(registerPage.isPasswordErrorDisplayed(), "Password error not shown");
    }

    @Step("Submit form with short password and verify password length error")
    private void submitShortPasswordAndVerifyError(RegisterPage registerPage,
                                                    String firstName, String lastName,
                                                    String email, String telephone, String shortPassword) {
        DriverManager.getDriver().navigate().refresh();
        registerPage.fillWithShortPassword(firstName, lastName, email, telephone, shortPassword);
        String pwdError = registerPage.getPasswordErrorText();
        Assert.assertTrue(pwdError.contains("Password must be between 4 and 20 characters!"),
                "Password length error not shown. Actual: " + pwdError);
    }
}

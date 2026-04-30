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
public class RegistrationSuccessTest extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return ExcelReader.getTestData("Registration");
    }

    @Test(dataProvider = "registrationData")
    @Severity(SeverityLevel.CRITICAL)
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
        new HomePage(DriverManager.getDriver()).goToRegister();
    }

    @Step("Fill registration form with valid data")
    private void fillRegistrationForm(String firstName, String lastName, String email,
                                       String telephone, String password, String confirmPassword) {
        new RegisterPage(DriverManager.getDriver()).register(firstName, lastName, email, telephone, password, confirmPassword);
    }

    @Step("Verify success message is displayed")
    private void verifySuccessMessage() {
        String successMsg = new RegisterPage(DriverManager.getDriver()).getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your Account Has Been Created!"),
                "Expected success message not found. Actual: " + successMsg);
    }

    @Step("Verify logout option is available and log out")
    private void verifyLogout() {
        new HomePage(DriverManager.getDriver()).logout();
    }
}

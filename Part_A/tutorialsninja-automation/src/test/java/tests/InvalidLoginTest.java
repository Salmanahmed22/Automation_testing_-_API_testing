package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

@Epic("User Management")
public class InvalidLoginTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Invalid Login")
    @Description("Verify error message appears when logging in with wrong credentials")
    public void testInvalidLogin() {
        Object[][] data = ExcelReader.getTestData("Login");
        String email    = (String) data[1][0];
        String password = (String) data[1][1];

        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);

        String errorMsg = loginPage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("No match for E-Mail Address and/or Password."),
                "Expected login error not found. Actual: " + errorMsg);
    }
}

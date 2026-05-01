package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

@Epic("User Management")
@Feature("Authentication")
public class ValidLoginTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Story("Valid Login")
    @Description("Verify user can log in with valid credentials and reaches the My Account page")
    public void testValidLogin() {
        Object[][] data = ExcelReader.getTestData("Login");
        String email    = (String) data[0][0];
        String password = (String) data[0][1];

        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);

        AccountPage accountPage = new AccountPage(DriverManager.getDriver());
        Assert.assertTrue(accountPage.isAccountPageDisplayed(),
                "Account page not displayed after valid login");

        homePage.logout();
    }
}

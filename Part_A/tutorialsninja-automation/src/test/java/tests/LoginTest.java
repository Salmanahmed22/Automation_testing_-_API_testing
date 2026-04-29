package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

@Epic("User Management")
public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelReader.getTestData("Login");
    }

    @Test(dataProvider = "loginData")
    @Story("Login Functionality")
    @Description("Test login with valid and invalid credentials")
    public void testLogin(String email, String password, String expectedResult) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);

        if (expectedResult.equals("success")) {
            AccountPage accountPage = new AccountPage(DriverManager.getDriver());
            Assert.assertTrue(accountPage.isAccountPageDisplayed(),
                    "Account page not displayed after valid login");
            homePage.logout();
        } else {
            String errorMsg = loginPage.getErrorMessage();
            Assert.assertTrue(errorMsg.contains("No match for E-Mail Address and/or Password."),
                    "Expected login error message not found. Actual: " + errorMsg);
        }
    }
}

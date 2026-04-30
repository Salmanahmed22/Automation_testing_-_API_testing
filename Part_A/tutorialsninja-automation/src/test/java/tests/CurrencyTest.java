package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.ConfigReader;
import utils.ExcelReader;

import java.util.List;

@Epic("Shopping Features")
public class CurrencyTest extends BaseTest {

    @DataProvider(name = "currencyData")
    public Object[][] currencyData() {
        return ExcelReader.getTestData("Currency");
    }

    @Test(dataProvider = "currencyData")
    @Story("Currency Change")
    @Description("Verify prices change when currency is switched")
    public void testCurrencyChange(String email, String password, String currency) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);
        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        homePage.goToDesktops();

        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        List<String> pricesInDollar = productListPage.getProductPrices();
        Assert.assertFalse(pricesInDollar.isEmpty(), "No prices found on page");

        homePage.changeCurrencyToEuro();

        List<String> pricesInEuro = productListPage.getProductPrices();
        Assert.assertFalse(pricesInEuro.isEmpty(), "No prices found after currency change");
        Assert.assertNotEquals(pricesInDollar, pricesInEuro, "Prices did not change after currency switch");

        homePage.logout();
    }
}

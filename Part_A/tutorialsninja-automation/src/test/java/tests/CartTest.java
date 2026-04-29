package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.ConfigReader;

@Epic("Shopping Features")
public class CartTest extends BaseTest {

    @Test
    @Story("Add Items to Cart")
    @Description("Add tablet and laptop to cart and verify total price")
    public void testAddItemsToCartAndVerifyTotal() throws InterruptedException {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));

        homePage.goToTablets();

        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        productListPage.addToCartByName("Samsung Galaxy Tab 10.1");

        Thread.sleep(2000);

        Assert.assertTrue(productListPage.isSuccessToastDisplayed(),
                "Success toast not shown after adding tablet");

        homePage.openCart();
        homePage.clickViewCart();

        CartPage cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isProductInCart("Samsung Galaxy Tab 10.1"),
                "Tablet not found in cart");

        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        homePage.goToLaptops();

        productListPage.addToCartByName("HP LP3065");

        Thread.sleep(2000);

        DriverManager.getDriver().get(ConfigReader.get("base.url"));
        homePage.openCart();
        homePage.clickViewCart();

        Assert.assertTrue(cartPage.isProductInCart("HP LP3065"), "Laptop not found in cart");

        String total = cartPage.getTotalPrice();
        Assert.assertNotNull(total, "Total price is null");
        Assert.assertFalse(total.isEmpty(), "Total price is empty");

        homePage.logout();
    }
}

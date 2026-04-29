package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.ExcelReader;

@Epic("Shopping Features")
public class CheckoutTest extends BaseTest {

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return ExcelReader.getTestData("Checkout");
    }

    @Test(dataProvider = "checkoutData")
    @Story("Checkout Process")
    @Description("Complete full checkout process and confirm order placement")
    public void testCheckoutProcess(String email, String password, String firstName,
                                    String lastName, String address, String city,
                                    String postcode, String country, String zone,
                                    String comment) throws InterruptedException {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);

        homePage.goToMP3Players();

        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        productListPage.addToCartByName("iPod Shuffle");

        Thread.sleep(2000);

        homePage.openCart();
        homePage.clickViewCart();

        CartPage cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isProductInCart("iPod Shuffle"),
                "iPod Shuffle not in cart");

        cartPage.getTotalPrice();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillBillingDetails(firstName, lastName, address, city, postcode, country, zone);

        Thread.sleep(1500);
        checkoutPage.continueShipping();

        Thread.sleep(1500);
        checkoutPage.continueDelivery(comment);

        Thread.sleep(1000);
        checkoutPage.agreeAndContinuePayment();

        Thread.sleep(1000);
        checkoutPage.confirmOrder();

        String successMsg = checkoutPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your order has been placed!"),
                "Order confirmation message not found. Actual: " + successMsg);

        homePage.logout();
    }
}

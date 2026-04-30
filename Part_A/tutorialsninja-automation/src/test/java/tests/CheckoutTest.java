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
import utils.ConfigReader;
import utils.ExcelReader;

@Epic("Shopping Features")
public class CheckoutTest extends BaseTest {

    @DataProvider(name = "checkoutData")
    public Object[][] checkoutData() {
        return ExcelReader.getTestData("Checkout");
    }

    @Test(dataProvider = "checkoutData")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Checkout Process")
    @Description("Complete full checkout process and confirm order placement")
    public void testCheckoutProcess(String email, String password,
                                    String firstName, String lastName,
                                    String address, String city, String postcode,
                                    String country, String zone, String comment,
                                    String category, String product) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();
        new LoginPage(DriverManager.getDriver()).login(email, password);
        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        addProductToCart(homePage, category, product);
        verifyProductInCart(homePage, product);
        proceedToCheckout(firstName, lastName, address, city, postcode, country, zone, comment);
        verifyOrderConfirmation();

        homePage.logout();
    }

    @Step("Navigate to {category} and add {product} to cart")
    private void addProductToCart(HomePage homePage, String category, String product) {
        homePage.navigateToCategoryByName(category);
        new ProductListPage(DriverManager.getDriver()).addToCartByName(product);
        new ProductListPage(DriverManager.getDriver()).isSuccessToastDisplayed();
    }

    @Step("Open cart and verify {product} is present, then proceed to checkout")
    private void verifyProductInCart(HomePage homePage, String product) {
        homePage.openCart();
        homePage.clickViewCart();
        CartPage cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isProductInCart(product), product + " not in cart");
        cartPage.getTotalPrice();
        cartPage.clickCheckout();
    }

    @Step("Fill billing details and proceed through all checkout steps")
    private void proceedToCheckout(String firstName, String lastName, String address,
                                    String city, String postcode, String country,
                                    String zone, String comment) {
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        checkoutPage.fillBillingDetails(firstName, lastName, address, city, postcode, country, zone);
        checkoutPage.continueShipping();
        checkoutPage.continueDelivery(comment);
        checkoutPage.agreeAndContinuePayment();
        checkoutPage.confirmOrder();
    }

    @Step("Verify order confirmation message is displayed")
    private void verifyOrderConfirmation() {
        String successMsg = new CheckoutPage(DriverManager.getDriver()).getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Your order has been placed!"),
                "Order confirmation message not found. Actual: " + successMsg);
    }
}

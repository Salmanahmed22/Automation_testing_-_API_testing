package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.ConfigReader;
import utils.ExcelReader;

@Epic("Shopping Features")
public class CartTest extends BaseTest {

    @DataProvider(name = "cartData")
    public Object[][] cartData() {
        return ExcelReader.getTestData("Cart");
    }

    @Test(dataProvider = "cartData")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Items to Cart")
    @Description("Add two products from different categories to cart and verify total price")
    public void testAddItemsToCartAndVerifyTotal(String email, String password,
                                                  String category1, String product1,
                                                  String category2, String product2) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();
        new LoginPage(DriverManager.getDriver()).login(email, password);
        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        addProductToCart(homePage, category1, product1);
        verifyCartContains(homePage, product1);

        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        addProductToCart(homePage, category2, product2);

        DriverManager.getDriver().get(ConfigReader.get("base.url"));
        homePage.openCart();
        homePage.clickViewCart();

        CartPage cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isProductInCart(product1),
                product1 + " not found in cart after adding both items");
        Assert.assertTrue(cartPage.isProductInCart(product2),
                product2 + " not found in cart");

        String total = cartPage.getTotalPrice();
        Assert.assertNotNull(total, "Total price is null");
        Assert.assertFalse(total.isEmpty(), "Total price is empty");

        homePage.logout();
    }

    @Step("Navigate to {category} and add {product} to cart")
    private void addProductToCart(HomePage homePage, String category, String product) {
        homePage.navigateToCategoryByName(category);
        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        productListPage.addToCartByName(product);
        Assert.assertTrue(productListPage.isSuccessToastDisplayed(),
                "Success toast not shown after adding: " + product);
    }

    @Step("Open cart and verify {product} is present")
    private void verifyCartContains(HomePage homePage, String product) {
        homePage.openCart();
        homePage.clickViewCart();
        CartPage cartPage = new CartPage(DriverManager.getDriver());
        Assert.assertTrue(cartPage.isProductInCart(product),
                product + " not found in cart after first add");
    }
}

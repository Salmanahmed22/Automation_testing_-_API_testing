package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductListPage;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.List;

@Epic("Shopping Features")
@Feature("Product Listing")
public class SortTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Story("Sort Products")
    @Description("Verify products are sorted correctly by name A-Z and Z-A on the Phones page")
    public void testSortByName() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        new LoginPage(DriverManager.getDriver())
                .login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));

        homePage.goToPhones();
        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());

        verifySortAscending(productListPage);
        verifySortDescending(productListPage);

        homePage.logout();
    }

    @Step("Sort by Name (A - Z) and verify ascending order")
    private void verifySortAscending(ProductListPage productListPage) {
        productListPage.sortBy("Name (A - Z)");
        List<String> names = productListPage.getProductNames();
        List<String> expected = new ArrayList<>(names);
        expected.sort(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(names, expected, "Products not sorted A-Z correctly");
    }

    @Step("Sort by Name (Z - A) and verify descending order")
    private void verifySortDescending(ProductListPage productListPage) {
        productListPage.sortBy("Name (Z - A)");
        List<String> names = productListPage.getProductNames();
        List<String> expected = new ArrayList<>(names);
        expected.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        Assert.assertEquals(names, expected, "Products not sorted Z-A correctly");
    }
}

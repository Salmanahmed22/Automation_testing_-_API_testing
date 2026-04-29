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
import utils.ExcelReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Epic("Shopping Features")
public class SortTest extends BaseTest {

    @DataProvider(name = "sortData")
    public Object[][] sortData() {
        return ExcelReader.getTestData("SortBy");
    }

    @Test(dataProvider = "sortData")
    @Story("Sort Products")
    @Description("Verify products are sorted correctly by name ascending and descending")
    public void testSortByName(String email, String password, String sortOrder) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(email, password);

        homePage.goToPhones();

        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        productListPage.sortBy(sortOrder);

        List<String> names = productListPage.getProductNames();

        if (sortOrder.contains("A - Z")) {
            List<String> sorted = new ArrayList<>(names);
            Collections.sort(sorted);
            Assert.assertEquals(names, sorted, "Products not sorted A-Z correctly");
        } else {
            List<String> sorted = new ArrayList<>(names);
            sorted.sort(Collections.reverseOrder());
            Assert.assertEquals(names, sorted, "Products not sorted Z-A correctly");
        }

        homePage.logout();
    }
}

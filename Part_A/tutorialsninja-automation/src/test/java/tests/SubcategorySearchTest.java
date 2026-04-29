package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;

@Epic("Shopping Features")
public class SubcategorySearchTest extends BaseTest {

    @Test
    @Story("Search in Subcategories")
    @Description("Verify search in subcategories finds Apple Cinema 30")
    public void testSearchInSubcategories() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));

        homePage.searchFor("Apple");

        SearchResultsPage searchPage = new SearchResultsPage(DriverManager.getDriver());
        searchPage.selectCategory("Components");
        searchPage.searchWithKeyword("Apple");

        Assert.assertTrue(searchPage.getProductNames().isEmpty()
                        || searchPage.getNoResultsMessage().contains("There is no product"),
                "Expected no results without subcategory search");

        searchPage.checkSearchInSubcategories();
        searchPage.searchWithKeyword("Apple");

        Assert.assertTrue(searchPage.isProductDisplayed("Apple Cinema 30"),
                "Apple Cinema 30 not found when searching in subcategories");

        homePage.logout();
    }
}

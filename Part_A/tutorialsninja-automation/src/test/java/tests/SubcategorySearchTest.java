package tests;

import base.BaseTest;
import base.DriverManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchResultsPage;
import utils.ConfigReader;
import utils.ExcelReader;

@Epic("Shopping Features")
@Feature("Search")
public class SubcategorySearchTest extends BaseTest {

    @DataProvider(name = "subcategoryData")
    public Object[][] subcategoryData() {
        return ExcelReader.getTestData("Subcategory");
    }

    @Test(dataProvider = "subcategoryData")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search in Subcategories")
    @Description("Verify that searching in subcategories finds products not visible in top-level category search")
    public void testSearchInSubcategories(String email, String password,
                                           String keyword, String category,
                                           String expectedProduct) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();
        new LoginPage(DriverManager.getDriver()).login(email, password);
        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        homePage.searchFor(keyword);

        SearchResultsPage searchPage = new SearchResultsPage(DriverManager.getDriver());
        verifyNoResultsWithoutSubcategory(searchPage, keyword, category);
        verifyProductFoundInSubcategory(searchPage, keyword, expectedProduct);

        homePage.logout();
    }

    @Step("Search '{keyword}' in category '{category}' without subcategory — expect no results")
    private void verifyNoResultsWithoutSubcategory(SearchResultsPage searchPage,
                                                    String keyword, String category) {
        searchPage.selectCategory(category);
        searchPage.searchWithKeyword(keyword);
        Assert.assertTrue(
                searchPage.getProductNames().isEmpty()
                        || searchPage.getNoResultsMessage().contains("There is no product"),
                "Expected no results without subcategory search");
    }

    @Step("Enable 'Search in Subcategories' and verify '{expectedProduct}' is found")
    private void verifyProductFoundInSubcategory(SearchResultsPage searchPage,
                                                  String keyword, String expectedProduct) {
        searchPage.checkSearchInSubcategories();
        searchPage.searchWithKeyword(keyword);
        Assert.assertTrue(searchPage.isProductDisplayed(expectedProduct),
                expectedProduct + " not found when searching in subcategories");
    }
}

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

import java.util.List;

@Epic("Shopping Features")
public class SearchTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return ExcelReader.getTestData("Search");
    }

    @Test(dataProvider = "searchData")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Search Functionality")
    @Description("Verify search returns relevant products")
    public void testSearchByName(String keyword, String expectedProduct) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));
        DriverManager.getDriver().get(ConfigReader.get("base.url"));

        homePage.searchFor(keyword);

        SearchResultsPage searchPage = new SearchResultsPage(DriverManager.getDriver());
        List<String> results = searchPage.getProductNames();
        Assert.assertFalse(results.isEmpty(), "No search results found for: " + keyword);

        boolean allMatch = results.stream()
                .allMatch(name -> name.toLowerCase().contains(keyword.toLowerCase()));
        Assert.assertTrue(allMatch, "Some products do not contain keyword: " + keyword);
    }
}

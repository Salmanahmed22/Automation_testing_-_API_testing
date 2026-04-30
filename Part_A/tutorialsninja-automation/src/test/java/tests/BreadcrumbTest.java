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

@Epic("Navigation")
public class BreadcrumbTest extends BaseTest {

    @DataProvider(name = "breadcrumbData")
    public Object[][] breadcrumbData() {
        return ExcelReader.getTestData("Breadcrumb");
    }

    @Test(dataProvider = "breadcrumbData")
    @Severity(SeverityLevel.MINOR)
    @Story("Breadcrumb and Left Menu")
    @Description("Verify breadcrumb last item and left menu highlight match the navigated category")
    public void testBreadcrumbAndLeftMenu(String email, String password,
                                          String category, String expectedBreadcrumb) {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();
        new LoginPage(DriverManager.getDriver()).login(email, password);

        navigateToCategory(homePage, category);
        verifyBreadcrumb(expectedBreadcrumb);
        verifyLeftMenu(expectedBreadcrumb);

        homePage.logout();
    }

    @Step("Navigate to category: {category}")
    private void navigateToCategory(HomePage homePage, String category) {
        homePage.navigateToCategoryByName(category);
    }

    @Step("Verify breadcrumb last item contains '{expectedBreadcrumb}'")
    private void verifyBreadcrumb(String expectedBreadcrumb) {
        String breadcrumb = new ProductListPage(DriverManager.getDriver()).getBreadcrumbLastItem();
        Assert.assertTrue(breadcrumb.contains(expectedBreadcrumb),
                "Breadcrumb last item mismatch. Expected to contain: " + expectedBreadcrumb + ", Actual: " + breadcrumb);
    }

    @Step("Verify left menu active item contains '{expectedBreadcrumb}'")
    private void verifyLeftMenu(String expectedBreadcrumb) {
        String leftMenu = new ProductListPage(DriverManager.getDriver()).getLeftMenuActiveItem();
        Assert.assertTrue(leftMenu.contains(expectedBreadcrumb),
                "Left menu active item mismatch. Expected to contain: " + expectedBreadcrumb + ", Actual: " + leftMenu);
    }
}

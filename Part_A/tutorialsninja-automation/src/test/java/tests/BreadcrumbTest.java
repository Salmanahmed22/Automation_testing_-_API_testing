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

@Epic("Navigation")
public class BreadcrumbTest extends BaseTest {

    @Test
    @Story("Breadcrumb and Left Menu")
    @Description("Verify breadcrumb and left menu highlight when navigating to Tablets")
    public void testBreadcrumbAndLeftMenu() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        homePage.goToLogin();

        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigReader.get("valid.email"), ConfigReader.get("valid.password"));

        homePage.goToTablets();

        ProductListPage productListPage = new ProductListPage(DriverManager.getDriver());
        String breadcrumb = productListPage.getBreadcrumbLastItem();
        Assert.assertTrue(breadcrumb.contains("Tablets"),
                "Breadcrumb last item is not Tablets. Actual: " + breadcrumb);

        String leftMenu = productListPage.getLeftMenuActiveItem();
        Assert.assertTrue(leftMenu.contains("Tablets"),
                "Left menu active item is not Tablets. Actual: " + leftMenu);

        homePage.logout();
    }
}

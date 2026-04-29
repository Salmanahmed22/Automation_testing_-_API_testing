package base;

import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.ScreenshotUtil;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(ConfigReader.get("base.url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            saveScreenshotOnFailure();
        }
        DriverManager.quitDriver();
    }

    @Attachment(value = "Screenshot on Failure", type = "image/png")
    private byte[] saveScreenshotOnFailure() {
        return ScreenshotUtil.takeScreenshot(DriverManager.getDriver());
    }
}

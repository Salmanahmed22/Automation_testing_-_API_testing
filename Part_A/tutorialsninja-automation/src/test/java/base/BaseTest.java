package base;

import io.qameta.allure.Allure;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.ScreenshotUtil;

import java.io.ByteArrayInputStream;

public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        DriverManager.getDriver().get(ConfigReader.get("base.url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = ScreenshotUtil.takeScreenshot(DriverManager.getDriver());
            Allure.addAttachment("Screenshot on Failure", "image/png",
                    new ByteArrayInputStream(screenshot), "png");

            Allure.addAttachment("Page URL on Failure",
                    DriverManager.getDriver().getCurrentUrl());

            Throwable cause = result.getThrowable();
            Allure.addAttachment("Failure Log",
                    cause != null ? cause.toString() : "Unknown failure");
        }
        DriverManager.quitDriver();
    }
}

package test.BaseTest;

import engine.BrowserFactory.driverSetupAndOptions.SetupDriver;
import engine.constants.FrameWorkConstants;
import engine.listeners.TestngListener;
import engine.logger.CustomLogger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;

@Listeners(TestngListener.class)
public class BaseTests {
    public WebDriver driver;
    public String testData = FrameWorkConstants.TEST_DATA;

    @BeforeClass
    @Parameters("browser")
    public void initDriver(ITestContext context, @Optional("chrome") String browser) throws MalformedURLException {
        if (FrameWorkConstants.DESTINATION.equalsIgnoreCase("local")) {
            if (FrameWorkConstants.BROWSER.equalsIgnoreCase("all")) {
                driver = new SetupDriver().initializeDriverWithBrowserChoice(browser);
            } else {
                driver = new SetupDriver().initializeDriver();
            }
        } else {
            if (FrameWorkConstants.BROWSER.equalsIgnoreCase("all")) {
                driver = new SetupDriver().initializeRemoteDriverWithBrowserChoice(browser);
            } else {
                driver = new SetupDriver().initializeRemoteDriver();
            }
        }
        CustomLogger.getLogger().info(driver);
        context.setAttribute("driver", driver);
    }

    @AfterMethod
    public void refreshAndDeleteCookies() {
        driver.manage().deleteAllCookies();
        CustomLogger.getLogger().info("delete all cookies");
    }

    @AfterClass
    public void tearDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}

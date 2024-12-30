
package engine.BrowserFactory.BrowserSetup;

import engine.BrowserFactory.driverSetupAndOptions.Options;
import engine.logger.CustomLogger;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.core.tools.CustomLoggerGenerator;
import org.apache.logging.log4j.core.tools.Generate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Chrome {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Chrome.class);
    private WebDriver driver;


    //TODO: Creates and configures ChromeOptions based on the provided list of options.
    //      Adds specific options like headless mode, maximized window, etc., if provided.
    //      Initializes a Chrome WebDriver instance using the configured options and logs the actions.
    //      If options are not provided, uses the default Chrome settings.
    public ChromeOptions createChromeOptions(List<String> options) {
        try {
            ChromeOptions chromeOptions = new ChromeOptions();
            if (options != null && !options.isEmpty()) {
                String message = String.format("Starting Chrome browser with options: %s", options);
                CustomLogger.getLogger().info(message);
                Allure.step(message);
                chromeOptions.addArguments(options);
            } else {
                String message = "Starting Chrome browser with default settings.";
                CustomLogger.getLogger().info(message);
                Allure.step(message);
            }
            return chromeOptions;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to create ChromeOptions. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw e;
        }
    }

    public WebDriver initDriver() {
        try {
            String message = "Initializing Chrome WebDriver.";
            CustomLogger.getLogger().info(message);
            Allure.step(message);

            driver = new ChromeDriver(createChromeOptions(Options.getBrowserOptions()));

            String successMessage = "Successfully initialized Chrome WebDriver.";
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);

            return driver;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to initialize Chrome WebDriver. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
    }
}

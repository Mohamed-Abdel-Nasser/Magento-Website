package engine.BrowserFactory.BrowserSetup;

import engine.BrowserFactory.driverSetupAndOptions.Options;
import engine.logger.CustomLogger;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

public class FireFox {

    private WebDriver driver;

    //TODO: Creates and configures FirefoxOptions based on the provided list of options.
    //      Adds options like headless mode, extensions, or others if provided.
    //      Initializes the Firefox WebDriver using the configured options and logs the actions.
    //      If no options are provided, the default Firefox settings are used.
    public FirefoxOptions createFirefoxOptions(List<String> options) {
        try {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (options != null && !options.isEmpty()) {
                String message = String.format("Starting Firefox browser with options: %s", options);
                CustomLogger.getLogger().info(message);
                Allure.step(message);
                firefoxOptions.addArguments(options);
            } else {
                String message = "Starting Firefox browser with default settings.";
                CustomLogger.getLogger().info(message);
                Allure.step(message);
            }
            return firefoxOptions;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to create FirefoxOptions. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw e;
        }
    }

    //TODO: Initializes the Firefox WebDriver instance.
    //      Logs the initialization process and any errors encountered.
    //      Uses the options provided by the Options class to configure the WebDriver.
    //      Returns the initialized WebDriver instance.
    public WebDriver initDriver() {
        try {
            String message = "Initializing Firefox WebDriver.";
            CustomLogger.getLogger().info(message);
            Allure.step(message);

            driver = new FirefoxDriver(createFirefoxOptions(Options.getBrowserOptions()));

            String successMessage = "Successfully initialized Firefox WebDriver.";
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);

            return driver;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to initialize Firefox WebDriver. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
    }
}
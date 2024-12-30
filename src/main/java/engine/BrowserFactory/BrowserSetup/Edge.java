package engine.BrowserFactory.BrowserSetup;

import engine.BrowserFactory.driverSetupAndOptions.Options;
import engine.logger.CustomLogger;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.List;

public class Edge {

    private WebDriver driver;

    //TODO: Creates and configures EdgeOptions based on the provided list of options.
    //      Adds specific options like headless mode, window maximization, etc., if available.
    //      Initializes an Edge WebDriver instance using the configured options and logs the actions.
    //      If no options are provided, defaults to the standard Edge settings.
    public EdgeOptions createEdgeOptions(List<String> options) {
        try {
            EdgeOptions edgeOptions = new EdgeOptions();
            if (options != null && !options.isEmpty()) {
                String message = String.format("Starting Edge browser with options: %s", options);
                CustomLogger.getLogger().info(message);
                Allure.step(message);
                edgeOptions.addArguments(options);
            } else {
                String message = "Starting Edge browser with default settings.";
                CustomLogger.getLogger().info(message);
                Allure.step(message);
            }
            return edgeOptions;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to create EdgeOptions. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw e;
        }
    }

    //TODO: Initializes the Edge WebDriver instance.
    //      Logs the initialization process and any errors encountered.
    //      Uses the options provided by the Options class to configure the WebDriver.
    //      Returns the initialized WebDriver instance.
    public WebDriver initDriver() {
        try {
            String message = "Initializing Edge WebDriver.";
            CustomLogger.getLogger().info(message);
            Allure.step(message);

            driver = new EdgeDriver(createEdgeOptions(Options.getBrowserOptions()));

            String successMessage = "Successfully initialized Edge WebDriver.";
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);

            return driver;
        } catch (Exception e) {
            String errorMessage = String.format("Failed to initialize Edge WebDriver. Error: %s", e.getMessage());
            CustomLogger.getLogger().error(errorMessage);
            Allure.step(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
    }
}
package engine.action;

import engine.logger.CustomLogger;
import org.openqa.selenium.WebDriver;

public class BrowserActions {

    /**
     * Navigates the WebDriver instance to the specified URL.
     * Validates the WebDriver instance and URL before attempting navigation.
     * Logs the action and handles errors gracefully.
     *
     * @param driver The WebDriver instance to use for navigation.
     * @param url    The URL to navigate to.
     * @throws IllegalArgumentException if the WebDriver or URL is invalid.
     * @throws RuntimeException         if navigation fails due to an unexpected error.
     */
    public static void navigateToURL(WebDriver driver, String url) {
        try {
            if (driver == null) {
                String errorMessage = "WebDriver instance is null. Cannot navigate to URL.";
                CustomLogger.getLogger().error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            if (url == null || url.trim().isEmpty()) {
                String errorMessage = "URL is null or empty. Cannot navigate to the specified URL.";
                CustomLogger.getLogger().error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            driver.navigate().to(url);

            String successMessage = "Successfully navigated to URL: " + url;
            CustomLogger.getLogger().info(successMessage);
        } catch (IllegalArgumentException e) {
            CustomLogger.getLogger().error("Invalid WebDriver or URL provided: " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            String errorMessage = "Failed to navigate to URL: " + url + ". Error: " + e.getMessage();
            CustomLogger.getLogger().error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }
}

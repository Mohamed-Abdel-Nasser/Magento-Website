package engine.action;

import engine.logger.CustomLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitActions {

    public static WebDriverWait waitForElement(WebDriver driver, int time, By locator, String condition) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));

        condition = condition.trim().toLowerCase(); // Normalize condition input for consistency
        CustomLogger.getLogger().info("Waiting for element: " + locator + " with condition: " + condition + " for " + time + " seconds");

        switch (condition) {
            case "visible":
                waitForVisibility(wait, driver, locator);
                break;
            case "invisible":
                waitForInvisibility(wait, driver, locator);
                break;
            case "clickable":
                waitForClickability(wait, driver, locator);
                break;
            case "present":
                waitForPresence(wait, locator);
                break;
            default:
                String errorMessage = "Unsupported wait condition: " + condition;
                CustomLogger.getLogger().error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
        }

        return wait;
    }


    public static WebDriverWait waitExplicitly(WebDriver driver, int time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        CustomLogger.getLogger().info("Wait for element to be ready for: " + time + " sec");
        return wait;
    }


    //TODO: Waits for the visibility of the specified element.
    //      If the element is not displayed, it waits for the element to become visible.
    //      Logs whether the element is already visible or becomes visible after the wait.
    private static void waitForVisibility(WebDriverWait wait, WebDriver driver, By locator) {
        if (!isElementDisplayed(driver, locator)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            CustomLogger.getLogger().info("Element is now visible: " + locator);
        } else {
            CustomLogger.getLogger().info("Element is already visible: " + locator);
        }
    }

    //TODO: Waits for the invisibility of the specified element.
    //      If the element is displayed, it waits for the element to become invisible.
    //      Logs whether the element is already invisible or becomes invisible after the wait.
    private static void waitForInvisibility(WebDriverWait wait, WebDriver driver, By locator) {
        if (isElementDisplayed(driver, locator)) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            CustomLogger.getLogger().info("Element is now invisible: " + locator);
        } else {
            CustomLogger.getLogger().info("Element is already invisible: " + locator);
        }
    }


    //TODO: Waits for the element to become clickable.
    //      If the element is not enabled, it first waits for the element to be visible, then waits until it is clickable.
    //      Logs the visibility status of the element before it becomes clickable.
    private static void waitForClickability(WebDriverWait wait, WebDriver driver, By locator) {
        if (!isElementEnabled(driver, locator)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            CustomLogger.getLogger().info("Element is now visible but not clickable yet: " + locator);
        }
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        CustomLogger.getLogger().info("Element is now clickable: " + locator);
    }


    //TODO: Waits until the specified element is present in the DOM.
    //      Uses WebDriverWait to wait for the presence of the element located by the given locator.
    //      Logs the action when the element becomes present.
    private static void waitForPresence(WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        CustomLogger.getLogger().info("Element is now present in the DOM: " + locator);
    }


    //TODO: Checks if the element located by the given locator is displayed on the page.
    //      Attempts to find the element and verify if it is visible. Returns true if displayed, false otherwise.
    //      Catches any exceptions and returns false if the element is not found or not displayed.
    private static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    //TODO: Checks if the element located by the given locator is enabled (interactable).
    //      Attempts to find the element and verify if it is enabled. Returns true if enabled, false otherwise.
    //      Catches any exceptions and returns false if the element is not found or not enabled.
    private static boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

}

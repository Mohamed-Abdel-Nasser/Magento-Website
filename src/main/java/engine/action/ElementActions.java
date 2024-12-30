package engine.action;

import engine.logger.CustomLogger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ElementActions {

    //TODO: Types the specified text into the input field located by the given locator.
    //      Waits for the element to be visible, clears the field, and then types the provided text.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void typeTextInField(WebDriver driver, By locator, String text, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");
            WebElement element = driver.findElement(locator);
            element.clear(); // Ensure the field is cleared before typing
            element.sendKeys(text);
            CustomLogger.getLogger().info("Typed: '" + text + "' in field: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type text in field: " + locator, e);
            throw new RuntimeException("Type action failed", e);
        }
    }

    //TODO: Retrieves the visible text from the web element located by the given locator.
    //      Waits for the element to be visible, then extracts and returns the text from the element.
    //      Logs the text value if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static String getText(WebDriver driver, By locator, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");
            String text = driver.findElement(locator).getText();
            CustomLogger.getLogger().info("Read text: '" + text + "' from field: " + locator);
            return text;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to get text from field: " + locator, e);
            throw new RuntimeException("Get text action failed", e);
        }
    }

    //TODO: Clicks on the element located by the given locator.
    //      Waits for the element to be clickable, then performs the click action.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void click(WebDriver driver, By locator, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "clickable");
            driver.findElement(locator).click();
            CustomLogger.getLogger().info("Clicked on element: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on element: " + locator, e);
            throw new RuntimeException("Click action failed", e);
        }
    }

    //TODO: Simulates pressing the ENTER key on the element located by the given locator.
    //      Finds the element and sends the ENTER key.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void clickEnter(WebDriver driver, By locator) {
        try {
            driver.findElement(locator).sendKeys(Keys.ENTER);
            CustomLogger.getLogger().info("Pressed ENTER on element: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to press ENTER on element: " + locator, e);
            throw new RuntimeException("Click ENTER action failed", e);
        }
    }

    //TODO: Clicks on the element using JavaScript Executor.
    //      Waits for the element to be visible, then performs the click action using JS executor.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void useJavaExecutorToClick(WebDriver driver, By locator, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            CustomLogger.getLogger().info("Clicked on element using JS executor: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click using JS executor on element: " + locator, e);
            throw new RuntimeException("JavaScript Executor Click failed", e);
        }
    }

    //TODO: Types the specified text in the element using JavaScript Executor.
    //      Waits for the element to be visible, then sets the value of the element using JS executor.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void useJavaExecutorToType(WebDriver driver, By locator, String text, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].value=arguments[1];", element, text);
            CustomLogger.getLogger().info("Typed in element using JS executor: " + locator + " with text: " + text);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type using JS executor on element: " + locator, e);
            throw new RuntimeException("JavaScript Executor Type failed", e);
        }
    }

    //TODO: Scrolls the page to the element specified by the locator using JavaScript Executor.
    //      Waits for the element to be visible within the specified time, then scrolls smoothly to bring it into view.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void scrollToElement(WebDriver driver, By locator, int time) {
        try {
            // Wait for the element to be visible
            WaitActions.waitForElement(driver, time, locator, "visible");
            WebElement element = driver.findElement(locator);
            if (!element.isDisplayed()) {
                throw new IllegalStateException("Element is not displayed: " + locator);
            }
            // Scroll to the element using JavaScript Executor
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                    element
            );
            CustomLogger.getLogger().info("Scrolled to element using JS executor: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to scroll to element: " + locator, e);
            throw new RuntimeException("JavaScript Executor Scroll failed", e);
        }
    }

    //TODO: Retrieves the value of a specified attribute from a web element located by the given locator.
    //      Finds the element using the provided locator and fetches the value of the specified attribute.
    //      Logs an info message with the attribute value if successful.
    //      Logs an error message and throws a RuntimeException if an error occurs.
    public static String getAttribute(WebDriver driver, By locator, String attribute) {
        try {
            String attrValue = driver.findElement(locator).getAttribute(attribute);
            CustomLogger.getLogger().info("Read attribute '" + attribute + "': '" + attrValue + "' for element: " + locator);
            return attrValue;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to get attribute '" + attribute + "' for element: " + locator, e);
            throw new RuntimeException("Get attribute action failed", e);
        }
    }

    //TODO: This method performs a hover action on a web element located by the given locator.
    //      It uses the Actions class to move the mouse pointer to the element, simulating a hover event.
    //      If the hover action is successful, it logs an info message.
    //      If an error occurs, it logs an error message and throws a RuntimeException.
    public static void hover(WebDriver driver, By locator) {
        try {
            new Actions(driver).moveToElement(driver.findElement(locator)).perform();
            CustomLogger.getLogger().info("Hovered over element: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to hover over element: " + locator, e);
            throw new RuntimeException("Hover action failed", e);
        }
    }

    //TODO: Checks if the element located by the given locator is displayed on the page.
    //      Attempts to find the element and verify if it is visible. Returns true if displayed, false otherwise.
    //      Logs the result if successful, otherwise catches any exceptions and returns false.
    public static Boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            driver.findElement(locator).isDisplayed();
            CustomLogger.getLogger().info("Element: " + locator + " is displayed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO: Checks if the element located by the given locator is enabled (interactable).
    //      Attempts to find the element and verify if it is enabled. Returns true if enabled, false otherwise.
    //      Logs the result if successful, otherwise catches any exceptions and returns false.
    public static Boolean isElementEnabled(WebDriver driver, By locator) {
        try {
            driver.findElement(locator).isEnabled();
            CustomLogger.getLogger().info("Element: " + locator + " is enabled");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //TODO: Selects an option in a dropdown by the specified index.
    //      Waits for the element to be visible, scrolls to the element, and then selects the option by index.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void handleSelection(WebDriver driver, By locator, double index, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");

            scrollToElement(driver, locator, time);
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            select.selectByIndex((int) index);
            CustomLogger.getLogger().info("Selected option by index: " + index + " in dropdown: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select option by index: " + index + " in dropdown: " + locator, e);
            throw new RuntimeException("Dropdown Selection by Index failed", e);
        }
    }

    //TODO: Selects an option from a dropdown element located by the given locator using the specified visible text value.
    //      Waits for the element to be visible, then selects the option by its visible text.
    //      Logs the action if successful. If an error occurs, logs the failure and throws a RuntimeException.
    public static void handleSelection(WebDriver driver, By locator, String value, int time) {
        try {
            WaitActions.waitForElement(driver, time, locator, "visible");
            WebElement element = driver.findElement(locator);
            Select select = new Select(element);
            select.selectByVisibleText(value);
            CustomLogger.getLogger().info("Selected option by value: " + value + " in dropdown: " + locator);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select option by value: " + value + " in dropdown: " + locator, e);
            throw new RuntimeException("Dropdown Selection by Value failed", e);
        }
    }

    //TODO: Retrieves the text from the element at the specified index in a list of elements located by the given locator.
    //      Iterates through all elements matching the locator, stores their text in a list, and returns the text of the element at the given index.
    //      Logs the action with the index of the selected element.
    public static String getTextFromListOfElementsByIndex(WebDriver driver, By locator, int index) {
        ArrayList<String> list = new ArrayList<>();
        for (WebElement we : driver.findElements(locator)) {
            list.add(we.getText());
        }
        CustomLogger.getLogger().info("get text from element no: " + index);
        return list.get(index);
    }

    //TODO: Retrieves a specific element from a list of elements located by the given locator, using the provided index.
    //      Finds all elements matching the locator, checks if the index is within valid bounds, and returns the element at the specified index.
    //      Logs the action if successful. If the index is out of bounds or an error occurs, logs the issue and throws an exception.
    public static WebElement getCertainElementFromMultipleElementsByIndex(WebDriver driver, By locator, int index) {
        try {
            // Find all elements matching the locator
            List<WebElement> list = driver.findElements(locator);

            // Check if the index is within the bounds of the list
            if (index >= 0 && index < list.size()) {
                WebElement element = list.get(index);
                CustomLogger.getLogger().info("Successfully retrieved element at index " + index + " from elements: " + locator);
                return element;
            } else {
                CustomLogger.getLogger().warn("Index " + index + " is out of bounds for the element list: " + locator);
                throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for the element list");
            }
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to retrieve element at index " + index + " from elements: " + locator, e);
            throw new RuntimeException("Failed to retrieve element by index", e);
        }
    }

    // TODO: Retrieves the text content from all elements matching the specified locator on the page.
    //       Each element's text is extracted and added to a list, which is then returned.
    //       Logs the extracted texts for debugging purposes.
    //       Throws an exception if the operation fails.
    public static List<String> getTextFromListOfElements(WebDriver driver, By locator) {
        try {
            List<String> textList = driver.findElements(locator).stream().map(WebElement::getText).collect(Collectors.toList());
            CustomLogger.getLogger().info("Fetched text from elements: " + locator);
            return textList;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to fetch text from elements: " + locator, e);
            throw new RuntimeException("Get text from list failed", e);
        }
    }


    // TODO: Counts the number of elements specified by the locator on the page.
    //       Logs the count and returns it.
    //       Throws an exception if the operation fails.
    public static int countElements(WebDriver driver, By locator) {
        try {
            int count = driver.findElements(locator).size();
            CustomLogger.getLogger().info("Counted " + count + " elements with locator: " + locator);
            return count;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to count elements with locator: " + locator, e);
            throw new RuntimeException("Count elements failed", e);
        }
    }
}

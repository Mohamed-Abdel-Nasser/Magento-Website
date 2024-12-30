package pages;

import engine.action.BrowserActions;
import engine.action.ElementActions;
import engine.action.WaitActions;
import engine.logger.CustomLogger;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class InventoryPage extends HomePage {
    private static final By ALL_PRODUCT_ELEMENTS = By.xpath("//ol[@class='products list items product-items']//li");
    private static final By ALL_PRODUCTS_PRICES = By.xpath("//ol[@class='products list items product-items']//li//span[@class='price-wrapper ']");
    private static final By ALL_PRODUCT_NAMES = By.xpath("//ol[@class='products list items product-items']//li//strong");
    private static final By ALL_PRODUCTS_COLORS = By.xpath("//ol[@class='products list items product-items']//li//div[@class='swatch-attribute color']");
    private static final By ALL_PRODUCTS_SIZES = By.xpath("//ol[@class='products list items product-items']//li//div[@class='swatch-attribute size']");
    private static final By limiter = By.xpath("(//select[@id='limiter'])[2]");
    private static final By SORT_DROPDOWN_SELECTOR = By.id("sorter");
    private static final By COMPARE_BUTTON = By.xpath("//span[text()='Compare']");
    private static final By COMPARE_PAGE = By.xpath("//span[text()='Compare Products']");
    private static final By COMPARED_PRODUCT_ITEMS = By.xpath("//ol[@id='compare-items']//li");
    private static final By SUCCESS_MESSAGE = By.xpath("//div[@class='page messages']");
    private static final By ACTIVE_FILTER_INDICATOR = By.xpath("//span[@class='filter-value']");
    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    private By certainProduct(int index) {
        return By.xpath("(//ol[@class='products list items product-items']//li)[" + index + "]");
    }

    private By productByName(String prod) {
        return By.xpath("//ol[@class='products list items product-items']//li//a[normalize-space(text())='" + prod + "']");
    }

    private By leftSectionFilters(int filterType, int filterName) {
        return By.xpath("((//div[@id='narrow-by-list']//div[@role='presentation'])[" + filterType + "]//ol//li)[" + filterName + "]//a");
    }

    private By leftSectionFilters(String filter) {
        return By.xpath("//div[@id='narrow-by-list']//div[@role='presentation']//a[contains(text(),'" + filter + "')]");
    }

    private By leftSectionSizeAndColorFilters(String filter) {
        return By.xpath("//div[@id='narrow-by-list']//div[@role='presentation']//div[@option-label='" + filter + "']/parent::a");
    }

    private By certainProductCompare(int index) {
        return By.xpath("(//ol[@class='products list items product-items']//li//div[@class='actions-secondary']//a[@class='action tocompare'])[" + index + "]");
    }


    @Step("Count the number of compared items")
    public int getComparedItemsCount() {
        try {
            CustomLogger.getLogger().info("Starting the count of compared items.");
            int count = ElementActions.countElements(driver, COMPARED_PRODUCT_ITEMS);
            CustomLogger.getLogger().info("Successfully counted compared items: " + count);
            return count;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error while counting compared items: " + e.getMessage());
            throw e;
        }
    }

    @Step("Count the total number of products")
    public int getProductCount() {
        try {
            CustomLogger.getLogger().info("Waiting for visibility of product elements.");
            WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(ALL_PRODUCT_ELEMENTS));
            int count = ElementActions.countElements(driver, ALL_PRODUCT_ELEMENTS);
            CustomLogger.getLogger().info("Successfully counted total products: " + count);
            return count;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error while counting products: " + e.getMessage());
            throw e;
        }
    }

    @Step("Get product names from the page")
    public ArrayList<String> getProductsNames() {
        try {
            CustomLogger.getLogger().info("Waiting for the visibility of product elements on the page.");
            WebDriverWait wait = WaitActions.waitExplicitly(driver, 10); // Configurable wait time
            wait.until(ExpectedConditions.visibilityOfElementLocated(ALL_PRODUCT_ELEMENTS));

            CustomLogger.getLogger().info("Retrieving product names from the page.");
            ArrayList<String> productNames = (ArrayList<String>) ElementActions.getTextFromListOfElements(driver, ALL_PRODUCT_NAMES);

            CustomLogger.getLogger().info("Successfully retrieved product names: " + productNames);
            return productNames;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to retrieve product names: " + e.getMessage(), e);
            throw new RuntimeException("Error while fetching product names: " + e.getMessage(), e);
        }
    }

    @Step("Choose limiter index [{index}]")
    public InventoryPage setItemsPerPageOption(double index) {
        try {
            CustomLogger.getLogger().info("Selecting page limit at index: " + index);
            ElementActions.handleSelection(driver, limiter, (int) index, 10);

            CustomLogger.getLogger().info("Waiting for product elements to be clickable after selecting page limit.");
            WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.elementToBeClickable(ALL_PRODUCT_ELEMENTS));
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select page limit at index " + index + ": " + e.getMessage(), e);
            throw new RuntimeException("Error while choosing page limit: " + e.getMessage(), e);
        }
        return this;
    }

    @Step("Choose sorter index [{index}]")
    public InventoryPage chooseSorter(double index) {
        try {
            CustomLogger.getLogger().info("Selecting sorter at index: " + index);
            ElementActions.handleSelection(driver, SORT_DROPDOWN_SELECTOR, index, 5);

            CustomLogger.getLogger().info("Waiting for product names to be clickable after selecting sorter.");
            WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.elementToBeClickable(ALL_PRODUCT_NAMES));
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select sorter at index " + index + ": " + e.getMessage(), e);
            throw new RuntimeException("Error while choosing sorter: " + e.getMessage(), e);
        }
        return this;
    }

    @Step("Click product at index [{index}] for comparison")
    public InventoryPage clickCertainProductCompare(double index) {
        try {
            CustomLogger.getLogger().info("Scrolling to product at index: " + index);
            ElementActions.scrollToElement(driver, certainProduct((int) index), 10);

            CustomLogger.getLogger().info("Hovering over product at index: " + index);
            ElementActions.hover(driver, certainProduct((int) index));

            CustomLogger.getLogger().info("Waiting for the compare button to be visible.");
            WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(certainProductCompare((int) index)));

            CustomLogger.getLogger().info("Clicking on the product compare button at index: " + index);
            ElementActions.click(driver, certainProductCompare((int) index), 6);

            CustomLogger.getLogger().info("Waiting for success message after clicking compare.");
            WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click product at index " + index + " for comparison: " + e.getMessage(), e);
            throw new RuntimeException("Error while clicking product compare button: " + e.getMessage(), e);
        }
        return this;
    }

    @Step("Click on product with name [{name}]")
    public InventoryPage clickCertainProduct(String name) {
        try {
            CustomLogger.getLogger().info("Attempting to click on product with name: " + name);
            ElementActions.click(driver, productByName(name), 6);
            CustomLogger.getLogger().info("Successfully clicked on product: " + name);
        } catch (Exception e) {
            CustomLogger.getLogger().warn("Failed to click on product with name " + name + ", clicking on the first product instead.");
            ElementActions.click(driver, certainProduct(1), 8);
            CustomLogger.getLogger().info("Clicked on the first product due to failure.");
        }
        return this;
    }

    @Step("Apply filter [{filterType}]")
    public InventoryPage applyFilter(String filterType) {
        try {
            CustomLogger.getLogger().info("Waiting for filter option: " + filterType);
            WaitActions.waitExplicitly(driver, 3).until(ExpectedConditions.presenceOfElementLocated(leftSectionFilters(filterType)));

            CustomLogger.getLogger().info("Navigating to filter URL for filter type: " + filterType);
            BrowserActions.navigateToURL(driver, ElementActions.getAttribute(driver, leftSectionFilters(filterType), "href"));
            CustomLogger.getLogger().info("Applied filter: " + filterType);
        } catch (Exception e1) {
            try {
                CustomLogger.getLogger().info("Failed to apply filter, trying size and color filter: " + filterType);
                WaitActions.waitExplicitly(driver, 3).until(ExpectedConditions.presenceOfElementLocated(leftSectionSizeAndColorFilters(filterType)));
                BrowserActions.navigateToURL(driver, ElementActions.getAttribute(driver, leftSectionSizeAndColorFilters(filterType), "href"));
                CustomLogger.getLogger().info("Applied size and color filter: " + filterType);
            } catch (Exception e2) {
                CustomLogger.getLogger().warn("Both filters failed, applying the first filter.");
                BrowserActions.navigateToURL(driver, ElementActions.getAttribute(driver, leftSectionFilters(1, 1), "href"));
                CustomLogger.getLogger().info("Applied first available filter.");
            }
        }
        return this;
    }

    @Step("Click on the Compare button")
    public InventoryPage clickCompareButton() {
        try {
            CustomLogger.getLogger().info("Scrolling to the Compare button.");
            ElementActions.scrollToElement(driver, COMPARE_BUTTON, 10);
            CustomLogger.getLogger().info("Clicking the Compare button.");
            ElementActions.click(driver, COMPARE_BUTTON, 5);
            CustomLogger.getLogger().info("Successfully clicked on Compare button.");
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on Compare button: " + e.getMessage());
            throw e;
        }
        return this;
    }

    @Step("Verify if the Compare page is displayed")
    public Boolean isComparePageDisplayed() {
        try {
            CustomLogger.getLogger().info("Checking if the Compare page is displayed.");
            Boolean isDisplayed = ElementActions.isElementDisplayed(driver, COMPARE_PAGE);
            if (isDisplayed) {
                CustomLogger.getLogger().info("Compare page is displayed successfully.");
            } else {
                CustomLogger.getLogger().warn("Compare page is not displayed.");
            }
            return isDisplayed;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error while checking Compare page visibility: " + e.getMessage());
            throw e;
        }
    }

    @Step("Verify if filters are applied")
    public Boolean areFiltersApplied() {
        try {
            CustomLogger.getLogger().info("Checking if filters are applied.");
            Boolean areFiltersApplied = ElementActions.isElementDisplayed(driver, ACTIVE_FILTER_INDICATOR);
            if (areFiltersApplied) {
                CustomLogger.getLogger().info("Filters are applied successfully.");
            } else {
                CustomLogger.getLogger().warn("No filters are applied.");
            }
            return areFiltersApplied;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error while checking if filters are applied: " + e.getMessage());
            throw e;
        }

    }
}

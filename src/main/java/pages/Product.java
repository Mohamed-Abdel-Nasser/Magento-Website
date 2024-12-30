package pages;

import engine.action.ElementActions;
import engine.action.WaitActions;
import engine.logger.CustomLogger;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Product extends HomePage {

    private static final By PRODUCT_NAME = By.xpath("//div[@class='product-info-main']//span[@itemprop='name']");
    private static final By PRODUCT_PRICE = By.xpath("//div[@class='product-info-main']//span[@class='price']");
    private static final By QTY_TEXT_AREA = By.id("qty");
    private static final By ADD_TO_CART_BTN = By.xpath("//span[text()='Add to Cart']");
    private static final By PRODUCT_SIZE_BTN = By.xpath("(//div[@class='swatch-attribute size']//div[@class='swatch-option text'])");
    private static final By PRODUCT_COLORS_BTN = By.xpath("//div[@class='swatch-attribute color']//div[@class='swatch-option color']");
    private static final By CART_COUNT = By.xpath("//span[@class='counter-number']");
    private static final By ADD_PRODUCT_MESSAGE = By.xpath("//div[@role='alert']");

    public Product(WebDriver driver) {
        super(driver);
    }

    private By getProductSizeByLabel(String size) {
        return By.xpath("//div[@class='swatch-attribute size']//div[@option-label='" + size + "']");
    }

    private By getProductSizeByIndex(int index) {
        return By.xpath("(//div[@class='swatch-attribute size']//div[@class='swatch-option text'])[" + index + "]");
    }

    private By getProductColorByLabel(String color) {
        return By.xpath("//div[@class='swatch-attribute color']//div[@option-label='" + color + "']");
    }

    private By getProductColorByIndex(int index) {
        return By.xpath("(//div[@class='swatch-attribute color']//div[@class='swatch-option color'])[" + index + "]");
    }

    @Step("Click 'Add to Cart' button")
    public Product clickAddToCart() {
        try {
            ElementActions.useJavaExecutorToClick(driver, ADD_TO_CART_BTN, 10);
            WaitActions.waitForElement(driver, 10, ADD_PRODUCT_MESSAGE, "visible");
            CustomLogger.getLogger().info("Successfully clicked 'Add to Cart' button");
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click 'Add to Cart' button", e);
            throw new RuntimeException("Error clicking 'Add to Cart' button", e);
        }
        return this;
    }

    @Step("Enter quantity: [{quantity}]")
    public Product typeQuantity(String quantity) {
        try {
            ElementActions.useJavaExecutorToType(driver, QTY_TEXT_AREA, quantity, 5);
            CustomLogger.getLogger().info("Successfully typed quantity: {}", quantity);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error typing quantity: {}", quantity, e);
            throw new RuntimeException("Error entering quantity", e);
        }
        return this;
    }

    @Step("Select size: [{size}]")
    public Product chooseSize(String size) {
        try {
            if (ElementActions.isElementDisplayed(driver, PRODUCT_SIZE_BTN)) {
                ElementActions.useJavaExecutorToClick(driver, getProductSizeByLabel(size), 5);
                CustomLogger.getLogger().info("Successfully selected size: {}", size);
            } else {
                CustomLogger.getLogger().info("No size elements found, skipping size selection");
            }
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select size: {}", size, e);
            ElementActions.useJavaExecutorToClick(driver, getProductSizeByIndex(1), 5);
            CustomLogger.getLogger().info("Selecting first available size in the list");
        }
        return this;
    }

    @Step("Select color: [{color}]")
    public Product chooseColor(String color) {
        try {
            if (ElementActions.isElementDisplayed(driver, PRODUCT_COLORS_BTN)) {
                ElementActions.scrollToElement(driver, getProductColorByLabel(color), 10);
                ElementActions.useJavaExecutorToClick(driver, getProductColorByLabel(color), 5);
                CustomLogger.getLogger().info("Successfully selected color: {}", color);
            } else {
                CustomLogger.getLogger().info("No color elements found, skipping color selection");
            }
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select color: {}", color, e);
            ElementActions.useJavaExecutorToClick(driver, getProductColorByIndex(1), 5);
            CustomLogger.getLogger().info("Selecting first available color in the list");
        }
        return this;
    }

    @Step("Get product price")
    public String getProductPrice() {
        try {
            WaitActions.waitForElement(driver, 5, PRODUCT_PRICE, "visible");
            String price = ElementActions.getText(driver, PRODUCT_PRICE, 10);
            CustomLogger.getLogger().info("Successfully retrieved product price: {}", price);
            return price;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error retrieving product price", e);
            throw new RuntimeException("Error retrieving product price: " + e.getMessage(), e);
        }
    }

    @Step("Get product name")
    public String getProductName() {
        try {
            WaitActions.waitForElement(driver, 5, PRODUCT_NAME, "visible");
            String name = ElementActions.getText(driver, PRODUCT_NAME, 10);
            CustomLogger.getLogger().info("Successfully retrieved product name: {}", name);
            return name;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error retrieving product name", e);
            throw new RuntimeException("Error retrieving product name: " + e.getMessage(), e);
        }
    }

}

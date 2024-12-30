package pages;

import engine.action.ElementActions;
import engine.logger.CustomLogger;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Cart extends HomePage {

    //TODO: Define locators for elements on the Cart page
    private static final By QUANTITY_TEXT_AREA = By.id("cart-324885-qty");
    private static final By UPDATE_SHOPPING_CART_BTN = By.xpath("//span[text()='Update Shopping Cart']");
    private static final By TOTAL_ROW = By.xpath("//tr[@class='price']");
    private static final By PROCEED_BTN = By.xpath("//span[text()='Proceed to Checkout']");


    //TODO: Constructor to initialize the Cart page object
    public Cart(WebDriver driver) {
        super(driver);
    }

    //TODO: Click the "Proceed to Checkout" button
    @Step("Click on [Proceed] button")
    public Cart clickProceedButton() {
        try {
            CustomLogger.getLogger().info("Attempting to click on the [Proceed] button: [{}]", PROCEED_BTN);
            // Perform the click action
            ElementActions.click(driver, PROCEED_BTN, 10);
            CustomLogger.getLogger().info("Successfully clicked on the [Proceed] button.");
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on the [Proceed] button: [{}]", PROCEED_BTN, e);
            throw new RuntimeException("Error clicking on the [Proceed] button. Ensure the element is visible and clickable.", e);
        }
        return this;
    }

    //TODO: Click the "Update Shopping Cart" button
    @Step("Click on [Update Shopping Cart] button")
    public Cart clickUpdateShoppingCart() {
        try {
            CustomLogger.getLogger().info("Attempting to click on the [Update Shopping Cart] button: [{}]", UPDATE_SHOPPING_CART_BTN);
            // Perform the click action
            ElementActions.click(driver, UPDATE_SHOPPING_CART_BTN, 10);
            CustomLogger.getLogger().info("Successfully clicked on the [Update Shopping Cart] button.");
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on the [Update Shopping Cart] button: [{}]", UPDATE_SHOPPING_CART_BTN, e);
            throw new RuntimeException("Error clicking on the [Update Shopping Cart] button. Ensure the element is visible and clickable.", e);
        }
        return this;
    }


    //TODO: Get the total price displayed in the cart
    @Step("Retrieve the total price displayed")
    public String getTotalPrice() {
        try {
            CustomLogger.getLogger().info("Attempting to retrieve the total price from the UI element: [{}]", TOTAL_ROW);
            String totalPrice = ElementActions.getText(driver, TOTAL_ROW, 10);
            if (totalPrice == null || totalPrice.trim().isEmpty()) {
                CustomLogger.getLogger().warn("Total price is either null or empty. Verify the element [{}] visibility or correctness.", TOTAL_ROW);
                return "";
            }
            CustomLogger.getLogger().info("Successfully retrieved total price: [{}]", totalPrice);
            return totalPrice;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to retrieve the total price from the UI element: [{}]", TOTAL_ROW, e);
            throw new RuntimeException("Error retrieving the total price. Ensure the element is visible and accessible.", e);
        }
    }

    //TODO: Enter the desired quantity into the quantity text area
    @Step("Enter quantity [{quantity}]")
    public Cart typeQuantity(String quantity) {
        if (quantity == null || quantity.trim().isEmpty()) {
            CustomLogger.getLogger().error("Invalid quantity provided: Quantity cannot be null or empty.");
            throw new IllegalArgumentException("Quantity cannot be null or empty.");
        }
        try {
            CustomLogger.getLogger().info("Attempting to type quantity: [{}] into the quantity field.", quantity);
            ElementActions.typeTextInField(driver, PROCEED_BTN, quantity, 10);
            CustomLogger.getLogger().info("Successfully typed quantity: [{}] in the quantity field.", quantity);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type quantity: [{}] in the quantity field.", quantity, e);
            throw new RuntimeException("Error typing quantity into the quantity field.", e);
        }
        return this;
    }

}

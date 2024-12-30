package pages;

import engine.action.ElementActions;
import engine.action.WaitActions;
import engine.logger.CustomLogger;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout extends HomePage {

    // TODO: Define locators in the Checkout page
    private static final By EMAIL_FIELD_TEXT_AREA = By.id("customer-email");
    private static final By NAME_FIELD_TEXT_AREA = By.name("firstname");
    private static final By STREET_1_TXT_AREA = By.name("street[0]");
    private static final By STREET_2_TEXT_AREA = By.name("street[1]");
    private static final By STREET_3_TEXT_AREA = By.name("street[2]");
    private static final By CITY_TEXT_AREA = By.name("city");
    private static final By lastname_txtArea = By.name("lastname");
    private static final By REGION_ID_DDL = By.name("region");
    private static final By TELEPHONE_TEXT_AREA = By.name("telephone");
    private static final By COUNTRY_ID_DDL = By.name("country_id");
    private static final By POSTCODE_TEXT_AREA = By.name("postcode");
    private static final By NEXT_BTN = By.xpath("//button[@class='button action continue primary']");
    private static final By PLACE_ORDER = By.xpath("//button//span[text()='Place Order']");
    private static final By CONTINUE_SHOPPING = By.xpath("//span[text()='Continue Shopping']");
    private static final By ORDER_NUMBER = By.xpath("//div[@class='checkout-success']//p//span");
    private static final By LOADING_IMG = By.xpath("//img[@alt='Loading...']");
    private static final By EMAIL_LOADER = By.xpath("//div[@class='loader']");
    private static final By PLACED_ORDER_SUCCESSFULLY_ROW = By.xpath("//span[text()='Thank you for your purchase!']");
    private static final By FREE_DELIVERY = By.xpath("(//input[@class='radio'])[1]");


    // TODO: Constructor to initialize the Checkout page
    public Checkout(WebDriver driver) {
        super(driver);
    }


    // TODO: Helper method to wait for loading indicators to disappear
    private void waitForLoader() {
        try {
            CustomLogger.getLogger().info("Waiting for loader to be visible.");
            WaitActions.waitForElement(driver, 10, LOADING_IMG, "visible");
            CustomLogger.getLogger().info("Loader is visible, now waiting for it to disappear.");
            WaitActions.waitForElement(driver, 10, LOADING_IMG, "invisible");
            CustomLogger.getLogger().info("Loader disappeared successfully.");
        } catch (Exception e) {
            CustomLogger.getLogger().error("Error waiting for the loader to disappear.", e);
            throw new RuntimeException("Failed to wait for loader to disappear.", e);
        }
    }


    // TODO: Method to type the email in the email field
    @Step("Type in email field [{email}]")
    public Checkout typeEmailField(String email) {
        if (email == null || email.trim().isEmpty()) {
            CustomLogger.getLogger().error("Email input is null or empty.");
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        try {
            CustomLogger.getLogger().info("Typing email: [{}] into the email field.", email);
            ElementActions.typeTextInField(driver, EMAIL_FIELD_TEXT_AREA, email, 10);
            waitForLoader();
            CustomLogger.getLogger().info("Successfully typed email: [{}] into the field.", email);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type email: [{}] into the field.", email, e);
            throw new RuntimeException("Error typing email: " + email, e);
        }

        return this;
    }


    // TODO: Method to type the first name in the name field
    @Step("Type in name field [{name}]")
    public Checkout typeNameField(String name) {
        if (name == null || name.trim().isEmpty()) {
            CustomLogger.getLogger().error("Name input is null or empty.");
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        try {
            CustomLogger.getLogger().info("Typing name: [{}] into the name field.", name);
            ElementActions.typeTextInField(driver, NAME_FIELD_TEXT_AREA, name, 10);
            CustomLogger.getLogger().info("Successfully typed name: [{}] into the field.", name);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type name: [{}] into the field.", name, e);
            throw new RuntimeException("Error typing name: " + name, e);
        }

        return this;
    }


    // TODO: Method to type the street address in the street field
    @Step("Type in street field [{street}]")
    public Checkout typeStreetField(String street) {
        // Validate street address input
        if (street == null || street.trim().isEmpty()) {
            CustomLogger.getLogger().warn("Provided street address is null or empty. Defaulting to '123 Default St'.");
        }
        try {
            CustomLogger.getLogger().info("Typing street address: [{}] into the street field.", street);
            ElementActions.typeTextInField(driver, STREET_1_TXT_AREA, street, 10);
            CustomLogger.getLogger().info("Successfully typed street address: [{}] into the field.", street);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type street address: [{}] into the field.", street, e);
            throw new RuntimeException("Error typing street address: " + street, e);
        }

        return this;
    }

    // TODO: Method to type the city in the city field
    @Step("Type in city field [{city}]")
    public Checkout typeCityField(String city) {
        // Validate city input
        if (city == null || city.trim().isEmpty()) {
            CustomLogger.getLogger().warn("Provided city is null or empty. Defaulting to 'Default City'.");
        }
        try {
            CustomLogger.getLogger().info("Typing city: [{}] into the city field.", city);
            ElementActions.typeTextInField(driver, CITY_TEXT_AREA, city, 10);
            CustomLogger.getLogger().info("Successfully typed city: [{}] into the field.", city);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type city: [{}] into the field.", city, e);
            throw new RuntimeException("Error typing city: " + city, e);
        }

        return this;
    }


    // TODO: Method to type the last name in the last name field
    @Step("Type in last name field [{lastName}]")
    public Checkout typeLastNameField(String lastName) {
        // Check for invalid input
        if (lastName == null || lastName.trim().isEmpty()) {
            CustomLogger.getLogger().error("Last name cannot be null or empty.");
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }

        try {
            CustomLogger.getLogger().info("Typing last name: [{}] into the last name field.", lastName);
            ElementActions.typeTextInField(driver, lastname_txtArea, lastName, 10);
            CustomLogger.getLogger().info("Successfully typed last name: [{}] into the field.", lastName);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type last name: [{}] into the field.", lastName, e);
            throw new RuntimeException("Error typing last name: " + lastName, e);
        }

        return this;
    }

    // TODO: Method to type the phone number in the phone field
    @Step("Type in phone field [{phone}]")
    public Checkout typePhoneField(String phone) {
        // Validate phone number format (basic validation example)
        if (phone == null || !phone.matches("\\+?\\d{10,15}")) {
            CustomLogger.getLogger().error("Invalid phone number format.");
            throw new IllegalArgumentException("Invalid phone number format.");
        }

        try {
            CustomLogger.getLogger().info("Typing phone number: [{}] into the phone field.", phone);
            ElementActions.typeTextInField(driver, TELEPHONE_TEXT_AREA, phone, 10);
            waitForLoader();
            CustomLogger.getLogger().info("Successfully typed phone number: [{}] into the field.", phone);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type phone number: [{}] into the field.", phone, e);
            throw new RuntimeException("Error typing phone number: " + phone, e);
        }

        return this;
    }


    // TODO: Method to type the postal code in the postal code field
    @Step("Type in postal code field [{postal}]")
    public Checkout typePostalCodeField(String postal) {
        try {
            if (postal == null || postal.trim().isEmpty()) {
                CustomLogger.getLogger().warn("Postal code is empty or null. Please provide a valid postal code.");
                throw new IllegalArgumentException("Postal code cannot be null or empty.");
            }
            CustomLogger.getLogger().info("Typing postal code [{}] into the postal code field.", postal);
            ElementActions.typeTextInField(driver, POSTCODE_TEXT_AREA, postal, 10);
            CustomLogger.getLogger().info("Successfully typed postal code [{}] into the postal code field.", postal);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to type postal code [{}] into the postal code field.", postal, e);
            throw new RuntimeException("Error occurred while typing postal code.", e);
        }

        return this;
    }


    // TODO: Method to choose a country from the dropdown
    @Step("Choose country [{country}]")
    public Checkout chooseCountry(String country, int index) {
        if (country == null || country.trim().isEmpty()) {
            CustomLogger.getLogger().warn("Provided country name is null or empty. Defaulting to index selection.");
            country = "Unknown Country"; // Default value in case of invalid input
        }
        try {
            CustomLogger.getLogger().info("Selecting country: [{}] at index [{}].", country, index);

            ElementActions.handleSelection(driver, COUNTRY_ID_DDL, index, 3);
            CustomLogger.getLogger().info("Successfully selected country: [{}] at index [{}].", country, index);
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select country: [{}] at index [{}].", country, index, e);
            throw new RuntimeException("Error selecting country: " + country + " at index: " + index, e);
        }

        return this;
    }

    // TODO: Method to choose a region from the dropdown
    @Step("Choose region: [{text}] at index [{index}]")
    public Checkout chooseRegion(String text, int index) {
        try {
            CustomLogger.getLogger().info("Attempting to select region [{}] from dropdown at index [{}].", text, index);
            ElementActions.typeTextInField(driver, REGION_ID_DDL, text, 5);
            CustomLogger.getLogger().info("Successfully selected region [{}] from dropdown at index [{}].", text, index);
            return this;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select region [{}] from dropdown at index [{}].", text, index, e);
            throw new RuntimeException("Error occurred while selecting region from dropdown.", e);
        }
    }

    // TODO: Method to select the free delivery option
    @Step("Choose [Free Delivery] checkbox")
    public Checkout chooseDelivery() {
        try {
            CustomLogger.getLogger().info("Attempting to select the [Free Delivery] checkbox.");
            ElementActions.useJavaExecutorToClick(driver, FREE_DELIVERY, 10);
            CustomLogger.getLogger().info("Successfully selected the [Free Delivery] checkbox.");
            return this;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to select the [Free Delivery] checkbox.", e);
            throw new RuntimeException("Error occurred while selecting the [Free Delivery] checkbox.", e);
        }
    }


    // TODO: Method to click on the next button
    @Step("Click on the [Next] button")
    public Checkout clickNextButton() {
        try {
            CustomLogger.getLogger().info("Attempting to click on the [Next] button using JavaScript Executor.");
            ElementActions.useJavaExecutorToClick(driver, NEXT_BTN, 10);
            CustomLogger.getLogger().info("Successfully clicked on the [Next] button.");
            return this;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on the [Next] button. Ensure the element is present and interactable.", e);
            throw new RuntimeException("Error occurred while clicking on the [Next] button.", e);
        }
    }

    // TODO: Method to click on the place order button
    @Step("Click on the [Place Order] button")
    public Checkout clickPlaceOrderButton() {
        try {
            CustomLogger.getLogger().info("Attempting to click on the [Place Order] button using JavaScript Executor.");
            ElementActions.useJavaExecutorToClick(driver, PLACE_ORDER, 10);
            CustomLogger.getLogger().info("Successfully clicked on the [Place Order] button.");
            return this;
        } catch (Exception e) {
            CustomLogger.getLogger().error("Failed to click on the [Place Order] button. Ensure the element is present and interactable.", e);
            throw new RuntimeException("Error occurred while clicking on the [Place Order] button.", e);
        }
    }

    // TODO: Method to retrieve the order number after placing an order
    @Step("Retrieve the order number after placing an order")
    public String getOrderNumber() {
        try {
            CustomLogger.getLogger().info("Attempting to retrieve the order number from the specified element.");
            String orderNumber = ElementActions.getText(driver, ORDER_NUMBER, 10);
            if (orderNumber != null && !orderNumber.trim().isEmpty()) {
                CustomLogger.getLogger().info("Successfully retrieved order number: " + orderNumber);
            } else {
                CustomLogger.getLogger().warn("Order number retrieved is empty or null. Verify the locator and element state.");
            }
            return orderNumber;
        } catch (Exception e) {
            CustomLogger.getLogger().error("An error occurred while retrieving the order number.", e);
            throw new RuntimeException("Failed to retrieve the order number. Ensure the element is present and visible.", e);
        }
    }

    // TODO: Method to check if the order has been created successfully
    @Step("Verify if the order has been created successfully")
    public boolean isOrderCreated() {
        try {
            CustomLogger.getLogger().info("Checking if the [Continue Shopping] button is displayed to confirm order creation.");
            boolean isDisplayed = ElementActions.isElementDisplayed(driver, CONTINUE_SHOPPING);
            if (isDisplayed) {
                CustomLogger.getLogger().info("Order creation confirmed. The [Continue Shopping] button is displayed.");
            } else {
                CustomLogger.getLogger().warn("Order creation could not be confirmed. The [Continue Shopping] button is not displayed.");
            }
            return isDisplayed;
        } catch (Exception e) {
            CustomLogger.getLogger().error("An error occurred while checking order creation status.", e);
            throw new RuntimeException("Failed to verify if the order has been created. Check element visibility and driver state.", e);
        }
    }

}

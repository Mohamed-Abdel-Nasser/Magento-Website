package pages;

import engine.action.BrowserActions;
import engine.action.ElementActions;
import engine.action.WaitActions;
import engine.constants.FrameWorkConstants;
import engine.logger.CustomLogger;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage {

    //TODO: Define the locators for essential elements on the homepage.
    private static final By SEARCH_TEXT_AREA = By.id("search");
    private static final By CART = By.xpath("//a[@class='action showcart']");
    private static final By LOGO = By.xpath("//a[@aria-label='store logo']");
    private static final By CHECKOUT = By.id("top-cart-btn-CHECKOUT");
    private static final By VIEW_AND_EDIT_CART = By.xpath("//span[text()='View and Edit Cart']");
    private static final By CART_TOTAL = By.xpath("//div[@class='items-total']");
    //TODO: WebDriver Declaration
    WebDriver driver;


    //TODO: Constructor to initialize the WebDriver and ensure it's not null.
    public HomePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver must not be null.");
        }
        this.driver = driver;
    }


    //TODO: Dynamically create XPath for navigation options based on given criteria and index.
    private By navigationOptions(String criteria, double index) {
        return By.xpath("(//div[@id='store.menu']//span[text()=\"" + criteria + "\"])[" + index + "]/parent::a");

    }


    //TODO: Navigate to the main website URL and wait for the logo to be visible.
    @Step("Navigate to website")
    public HomePage navigationToWebSite() {
        driver.get(FrameWorkConstants.URL);
        WaitActions.waitExplicitly(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(LOGO));
        CustomLogger.getLogger().info("Navigate to: {}", FrameWorkConstants.URL);
        return this;
    }


    //TODO: Navigate to a sub-link based on the criteria and index, then log the action.
    @Step("Navigate to [{crit}] numbered [{index}]")
    public HomePage navigateBetweenLinks(String crit, double index) {
        WaitActions.waitExplicitly(driver, 5).until(ExpectedConditions.presenceOfElementLocated(navigationOptions(crit, (int) index)));
        BrowserActions.navigateToURL(driver, ElementActions.getAttribute(driver, navigationOptions(crit, (int) index), "href"));
        CustomLogger.getLogger().info("Navigate to sub link: " + crit + " with occurence number: " + index);
        return this;
    }


    //TODO: Click on the cart and log the action.
    @Step("Click on [cart]")
    public HomePage clickOnCart() {
        ElementActions.click(driver, CART, 8);
        CustomLogger.getLogger().info("Click on cart");
        return this;
    }


    //TODO: Scroll to the checkout button and click on it using JavaScript Executor.
    @Step("Click on [checkOut]")
    public HomePage clickOnCheckOut() {
        ElementActions.scrollToElement(driver, CHECKOUT, 10);
        ElementActions.useJavaExecutorToClick(driver, CHECKOUT, 10);
        CustomLogger.getLogger().info("Click on CHECKOUT");
        return this;
    }


    //TODO: Click on "View and Edit Cart" and log the action.
    @Step("Click on [viewAndEdit]")
    public HomePage clickOnViewAndEdit() {
        ElementActions.click(driver, VIEW_AND_EDIT_CART, 6);
        CustomLogger.getLogger().info("Click on view and edit");
        return this;
    }


    //TODO: Type in the search bar, then simulate pressing the enter key to submit the search.
    @Step("Type in search bar [{text}] and click [enter]")
    public HomePage typeSearchBar(String text) {
        ElementActions.typeTextInField(driver, SEARCH_TEXT_AREA, text, 10);
        ElementActions.clickEnter(driver, SEARCH_TEXT_AREA);
        CustomLogger.getLogger().info("Type in search bar: " + text + " Then click enter");
        return this;
    }
}

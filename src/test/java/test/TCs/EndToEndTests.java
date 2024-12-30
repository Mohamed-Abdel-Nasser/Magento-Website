package test.TCs;

import engine.dataDriven.ReadExcel;
import engine.logger.CustomLogger;
import io.qameta.allure.Epic;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Checkout;
import pages.HomePage;
import pages.InventoryPage;
import pages.Product;
import test.BaseTest.BaseTests;

import java.util.Map;


public class EndToEndTests extends BaseTests {
    private HomePage homePage;
    private InventoryPage inventory;
    private Product product;
    private Checkout checkOut;

    @BeforeClass
    public void initObjetcs() {
        // Initialize page objects using a factory method or constructor
        homePage = new HomePage(driver);
        inventory = new InventoryPage(driver);
        product = new Product(driver);
        checkOut = new Checkout(driver);
        CustomLogger.getLogger().info("Page objects initialized successfully.");
    }


    @Epic("End to End")
    @Test(dataProvider = "EndToEnd")
    public void endToEnd(Map<String, String> data) {
        homePage.navigationToWebSite().navigateBetweenLinks(data.get("link"), Double.parseDouble(data.get("linkoccur")));

        inventory.clickCertainProduct(data.get("productname"));

        product
                .typeQuantity(data.get("quantity"))
                .chooseSize(data.get("size"))
                .chooseColor(data.get("color"))
                .clickAddToCart()
                .clickOnCart()
                .clickOnCheckOut();

        checkOut
                .typeNameField(data.get("name"))
                .typeLastNameField(data.get("lastname"))
                .typeStreetField(data.get("street"))
                .typeEmailField(data.get("email"))
                .typeCityField(data.get("city"))
                .typePostalCodeField(data.get("postal"))
                .chooseCountry(data.get("country"), 2)
                .chooseRegion("Arizona", 1)
                .typePhoneField(data.get("phone"))
                .chooseDelivery()
                .clickNextButton()
                .clickPlaceOrderButton()
                .isOrderCreated();

        String orderNumber = checkOut.getOrderNumber();
        Assert.assertNotEquals(orderNumber, "");
    }

    @Epic("First")
    @DataProvider(name = "EndToEnd")
    private Object[] data() {
        ReadExcel readExcel = new ReadExcel();
        return readExcel.readDataHashMapByRowCondition(testData, "EndToEnd", "LinkScript", "lnk0001");
    }
}

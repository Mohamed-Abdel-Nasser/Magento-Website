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

import java.util.ArrayList;
import java.util.Map;

public class Tests extends BaseTests {

    private HomePage homePage;
    private InventoryPage inventory;
    private Product product;
    private Checkout checkOut;

    @BeforeClass
    public void initObjects() {
        // Initialize page objects using a factory method or constructor for better maintainability
        homePage = new HomePage(driver);
        inventory = new InventoryPage(driver);
        product = new Product(driver);
        checkOut = new Checkout(driver);
        CustomLogger.getLogger().info("Page objects initialized successfully.");
    }

    // Test for verifying sorter functionality
    @Epic("Single tests")
    @Test(dataProvider = "sorter")
    public void assertSorter(Map<String, String> data) {
        homePage.navigationToWebSite().navigateBetweenLinks(data.get("link"), Double.parseDouble(data.get("linkoccur")));

        ArrayList<String> initialProductNames = inventory.getProductsNames();
        CustomLogger.getLogger().info("Initial product names: " + initialProductNames);

        inventory.chooseSorter(Double.parseDouble(data.get("sorter")));
        ArrayList<String> sortedProductNames = inventory.getProductsNames();
        CustomLogger.getLogger().info("Sorted product names: " + sortedProductNames);

        Assert.assertNotEquals(sortedProductNames, initialProductNames, "Product list is not sorted correctly.");
    }

    @DataProvider(name = "sorter")
    private Object[] sorterData() {
        ReadExcel readExcel = new ReadExcel();
        return readExcel.readDataHashMapByRowCondition(testData, "EndToEnd", "LinkScript", "lnk0005");
    }

    // Test for verifying item limiter functionality
    @Epic("Single tests")
    @Test(dataProvider = "limiter")
    public void assertLimiter(Map<String, String> data) {
        homePage.navigationToWebSite().navigateBetweenLinks(data.get("link"), Double.parseDouble(data.get("linkoccur")));

        int initialProductCount = inventory.getProductCount();
        CustomLogger.getLogger().info("Initial product count: " + initialProductCount);

        inventory.setItemsPerPageOption(Double.parseDouble(data.get("limiter")));

        int updatedProductCount = inventory.getProductCount();
        CustomLogger.getLogger().info("Updated product count: " + updatedProductCount);

        Assert.assertNotEquals(updatedProductCount, initialProductCount, "Product count is not updated after applying limiter.");
    }


    @DataProvider(name = "limiter")
    private Object[] limiterData() {
        ReadExcel readExcel = new ReadExcel();
        return readExcel.readDataHashMapByRowCondition(testData, "EndToEnd", "LinkScript", "lnk0003");
    }

    // Test for verifying filters functionality
    @Epic("Single tests")
    @Test(dataProvider = "filter")
    public void assertFilters(Map<String, String> data) {
        homePage.navigationToWebSite().navigateBetweenLinks(data.get("link"), Double.parseDouble(data.get("linkoccur")));

        ArrayList<String> initialProductNames = inventory.getProductsNames();
        CustomLogger.getLogger().info("Initial product names before applying filter: " + initialProductNames);

        inventory.applyFilter("XS");

        ArrayList<String> filteredProductNames = inventory.getProductsNames();
        CustomLogger.getLogger().info("Product names after applying filter: " + filteredProductNames);

        Assert.assertTrue(inventory.areFiltersApplied(), "Filters were not applied successfully.");
    }

    @DataProvider(name = "filter")
    private Object[] filterData() {
        ReadExcel readExcel = new ReadExcel();
        return readExcel.readDataHashMapByRowCondition(testData, "EndToEnd", "LinkScript", "lnk0004");
    }

    // Test for verifying compare functionality
    @Epic("Single tests")
    @Test(dataProvider = "compare")
    public void checkComparePage(Map<String, String> data) {
        homePage.navigationToWebSite().navigateBetweenLinks(data.get("link"), Double.parseDouble(data.get("linkoccur")));

        inventory.clickCertainProductCompare(Double.parseDouble(data.get("firstcompareitem"))).clickCertainProductCompare(Double.parseDouble(data.get("secondcompareitem")));

        int comparedItemsCount = inventory.getComparedItemsCount();
        inventory.clickCompareButton();

        Assert.assertTrue(inventory.isComparePageDisplayed(), "Compare page is not displayed correctly.");
        CustomLogger.getLogger().info("Number of compared items: " + comparedItemsCount);
    }


    @DataProvider(name = "compare")
    private Object[] compareData() {
        ReadExcel readExcel = new ReadExcel();
        return readExcel.readDataHashMapByRowCondition(testData, "EndToEnd", "LinkScript", "lnk0002");
    }
}

package engine.listeners;

import engine.logger.CustomLogger;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AllureListener implements ITestListener {

    public static void saveScreenShot(WebDriver driver, String text) {
        CustomLogger.getLogger().info("Taking screenshot and saving to allure report");
        Allure.addAttachment(text, new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
    }

    public static void saveTextLog(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Allure.addAttachment("logs", fis);
        } catch (FileNotFoundException e) {
        }
    }
}

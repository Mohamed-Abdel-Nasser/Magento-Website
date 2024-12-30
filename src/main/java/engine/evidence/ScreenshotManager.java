package engine.evidence;

import engine.constants.FrameWorkConstants;
import engine.logger.CustomLogger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotManager {

    //TODO: Capture and save a screenshot for the given test
    public static void takeScreenShotFolder(WebDriver driver, String testName) {
        //TODO: Take a screenshot from the WebDriver
        File SrcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //TODO: Format the current date and time for the screenshot file name
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String shotName = testName + " " + dtf.format(now) + ".png";

        //TODO: Set the destination file path for the screenshot
        File DestFile = new File(FrameWorkConstants.SCREENSHOTS_PATH + shotName);
        CustomLogger.getLogger().info("Saving screenshot to: " + FrameWorkConstants.SCREENSHOTS_PATH + shotName);
        try {
            //TODO: Copy the screenshot file to the destination path
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            CustomLogger.getLogger().error("can't save file");
        }
    }
}

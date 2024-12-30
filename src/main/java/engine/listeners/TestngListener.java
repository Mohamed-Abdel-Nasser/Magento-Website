package engine.listeners;

import engine.constants.FrameWorkConstants;
import engine.evidence.ScreenshotManager;
import engine.logger.CustomLogger;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TestngListener implements ITestListener, IExecutionListener, IRetryAnalyzer, IHookable {
    private static final int RETRY_LIMIT = 4;
    private static final String ALLURE_PATH = FrameWorkConstants.ALLURE_DIRECTORY;
    private static final String TEST_LOG_FILE = FrameWorkConstants.TEST_LOG_FILE;
    private static final String COMPLETE_LOG_FILE = FrameWorkConstants.COMPLETE_LOG_FILE;
    private static final String ALLURE_EXECUTABLE = FrameWorkConstants.ALLURE_EXECUTABLE;
    private static int retryCounter = 0;
    private int testcaseCount = 0;
    private int successfulTestCases = 0;
    private int failedTestCases = 0;
    private final int skippedTestCases = 0;
    private final ArrayList<String> success = new ArrayList<>();

    public void onTestStart(ITestResult result) {
        CustomLogger.getLogger().info("starting test: " + result.getName());
        testcaseCount++;
    }

    public void onTestSuccess(ITestResult result) {
        CustomLogger.getLogger().info("Test success hooray: " + result.getName());
        success.add(result.getName());
        AllureListener.saveTextLog(TEST_LOG_FILE);
        HelperListeners.deleteFile(TEST_LOG_FILE);
        successfulTestCases++;
    }

    public void onTestFailure(ITestResult result) {
        WebDriver mainDriver = (WebDriver) result.getTestContext().getAttribute("driver");
        failedTestCases++;
        CustomLogger.getLogger().info("Test failed: " + result.getName());

        //screenshot to folder
        ScreenshotManager.takeScreenShotFolder(mainDriver, result.getName());

        //screenshot in allure report
        AllureListener.saveScreenShot(mainDriver, result.getName() + " failure screenshot ");
        AllureListener.saveTextLog(TEST_LOG_FILE);
        retry(result);
        HelperListeners.deleteFile(TEST_LOG_FILE);
        CustomLogger.getLogger().info("retried test case for: " + retryCounter + " times");
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        WebDriver mainDriver = (WebDriver) iTestResult.getTestContext().getAttribute("driver");
        if (retryCounter <= RETRY_LIMIT) {
            retryCounter++;
            mainDriver.manage().deleteAllCookies();
            CustomLogger.getLogger().info("ended retry number: " + retryCounter);
            return true;
        }
        return false;
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            if (retry(testResult)) {
                callBack.runTestMethod(testResult);
            }
        }
    }

    public void onFinish(ITestContext context) {
        CustomLogger.getLogger().info("Finished " + testcaseCount + " test cases: " + Arrays.toString(HelperListeners.getAllTestMethodNames(context)));
        CustomLogger.getLogger().info(successfulTestCases + " test cases successful " + success);
        CustomLogger.getLogger().info(failedTestCases + " test cases failed" + Arrays.toString(HelperListeners.getFailedTestMethodNames(context)));
        CustomLogger.getLogger().info(skippedTestCases + " test cases skipped" + Arrays.toString(HelperListeners.getSkippedTestMethodNames(context)));
        HelperListeners.deleteFile(TEST_LOG_FILE);
    }

    public void onStart(ITestContext context) {

    }

    public void onExecutionStart() {
        HelperListeners.deleteFile(COMPLETE_LOG_FILE);
        HelperListeners.deleteDirectory(ALLURE_PATH);
    }

    public void onExecutionFinish() {
        if (FrameWorkConstants.OPEN_ALLURE.equalsIgnoreCase("true")) {
            CustomLogger.getLogger().info("start allure report pls don't stop the execution");
            HelperListeners.runFile(ALLURE_EXECUTABLE);
        }
    }
}


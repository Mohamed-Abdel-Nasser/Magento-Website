package engine.constants;

import engine.dataDriven.ReadExcel;
import engine.dataDriven.ReadProperties;

public class FrameWorkConstants {

    // TODO: Properties
    public static final String URL = ReadProperties.getProperty("url");
    public static final String PROPERTIES_PATH = ReadProperties.getProperty("propertiesPath");
    public static final String TEST_AUTOMATION_SETUP = ReadProperties.getProperty("testAutomationSetup");
    public static final String SCREENSHOTS_PATH = ReadProperties.getProperty("screenshotsPath");
    public static final String TEST_LOG_FILE = ReadProperties.getProperty("testsLogFile");
    public static final String ALLURE_DIRECTORY = ReadProperties.getProperty("allureDirectory");
    public static final String TEST_DATA = ReadProperties.getProperty("testData");
    public static final String COMPLETE_LOG_FILE = ReadProperties.getProperty("completeLogFile");
    public static final String ALLURE_EXECUTABLE = ReadProperties.getProperty("allureExec");

    // TODO: Setup data
    private static final ReadExcel READ_EXCEL = new ReadExcel();
    public static final String HEADLESS_MODE = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Headless").toLowerCase();
    public static final String MAXIMIZED = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Maximized");
    public static final String EXTENSIONS = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Extension");
    public static final String NO_SANDBOX = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "No sandbox");
    public static final String DISABLE_DEV_SHM = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Disable Dev Shm Usage");
    public static final String BROWSER = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Browser");
    public static final String OPEN_ALLURE = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Open allure");
    public static final String DESTINATION = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Proxy");
    public static final String PROXY_URL = READ_EXCEL.readCertainCell(TEST_AUTOMATION_SETUP, "Data", "Value", "Proxy url");

    static {
        ReadProperties.readAllFiles();
    }

}

package engine.BrowserFactory.driverSetupAndOptions;

import engine.BrowserFactory.BrowserSetup.Chrome;
import engine.BrowserFactory.BrowserSetup.Edge;
import engine.BrowserFactory.BrowserSetup.FireFox;
import engine.constants.FrameWorkConstants;
import engine.logger.CustomLogger;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class SetupDriver {

    public WebDriver driver;

    public WebDriver initializeDriver() {
        try {
            String browser = FrameWorkConstants.BROWSER != null ? FrameWorkConstants.BROWSER.trim().toLowerCase() : "";

            if (browser.isEmpty()) {
                String errorMessage = "Browser name is null, empty, or not specified.";
                CustomLogger.getLogger().error(errorMessage);
                Allure.step(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            WebDriver webDriver;

            switch (browser) {
                case "edge":
                    String edgeMessage = "Initializing Edge WebDriver.";
                    CustomLogger.getLogger().info(edgeMessage);
                    Allure.step(edgeMessage);
                    webDriver = new Edge().initDriver();
                    break;

                case "chrome":
                    String chromeMessage = "Initializing Chrome WebDriver.";
                    CustomLogger.getLogger().info(chromeMessage);
                    Allure.step(chromeMessage);
                    webDriver = new Chrome().initDriver();
                    break;

                case "firefox":
                    String firefoxMessage = "Initializing Firefox WebDriver.";
                    CustomLogger.getLogger().info(firefoxMessage);
                    Allure.step(firefoxMessage);
                    webDriver = new FireFox().initDriver();
                    break;

//                case "safari":
//                    String safariMessage = "Initializing Safari WebDriver.";
//                    CustomLogger.getLogger().info(safariMessage);
//                    Allure.step(safariMessage);
//                    webDriver = new Safari().initDriver();
//                    break;

                default:
                    String unsupportedMessage = "Unsupported or incorrect browser specified: " + browser;
                    CustomLogger.getLogger().fatal(unsupportedMessage);
                    Allure.step(unsupportedMessage);
                    throw new IllegalArgumentException(unsupportedMessage);
            }

            String successMessage = "Successfully initialized WebDriver for browser: " + browser;
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);
            return webDriver;

        } catch (IllegalArgumentException e) {
            CustomLogger.getLogger().error("Invalid browser name provided.", e);
            Allure.step("Invalid browser name provided.");
            throw e;
        } catch (Exception e) {
            String errorMessage = "Failed to start WebDriver for the specified browser: " + FrameWorkConstants.BROWSER + ". Error: " + e.getMessage();
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw new RuntimeException("Error occurred while starting WebDriver.", e);
        }
    }


    public RemoteWebDriver initializeRemoteDriver() {
        try {
            String browser = FrameWorkConstants.BROWSER != null ? FrameWorkConstants.BROWSER.trim().toLowerCase() : "";
            String url = FrameWorkConstants.PROXY_URL;

            if (url == null || url.isEmpty()) {
                String errorMessage = "Proxy URL is not specified or invalid.";
                CustomLogger.getLogger().error(errorMessage);
                Allure.step(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            RemoteWebDriver remoteWebDriver;
            switch (browser) {
                case "edge":
                    String edgeMessage = "Initializing remote Edge WebDriver.";
                    CustomLogger.getLogger().info(edgeMessage);
                    Allure.step(edgeMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new Edge().createEdgeOptions(Options.getBrowserOptions()));
                    break;

                case "chrome":
                    String chromeMessage = "Initializing remote Chrome WebDriver.";
                    CustomLogger.getLogger().info(chromeMessage);
                    Allure.step(chromeMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new Chrome().createChromeOptions(Options.getBrowserOptions()));
                    break;

                case "firefox":
                    String firefoxMessage = "Initializing remote Firefox WebDriver.";
                    CustomLogger.getLogger().info(firefoxMessage);
                    Allure.step(firefoxMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new FireFox().createFirefoxOptions(Options.getBrowserOptions()));
                    break;

//                case "safari":
//                    String safariMessage = "Initializing remote Safari WebDriver.";
//                    CustomLogger.getLogger().info(safariMessage);
//                    Allure.step(safariMessage);
//                    remoteWebDriver = new RemoteWebDriver(new URL(url), new SafariOptions());
//                    break;

                default:
                    String unsupportedMessage = "Unsupported or incorrect browser specified: " + browser;
                    CustomLogger.getLogger().error(unsupportedMessage);
                    Allure.step(unsupportedMessage);
                    throw new IllegalArgumentException(unsupportedMessage);
            }

            String successMessage = "Successfully initialized remote WebDriver for browser: " + browser;
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);
            return remoteWebDriver;

        } catch (MalformedURLException e) {
            String errorMessage = "Invalid Proxy URL: " + FrameWorkConstants.PROXY_URL;
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw new RuntimeException("Failed to create RemoteWebDriver due to malformed URL.", e);
        } catch (Exception e) {
            String errorMessage = "Failed to start remote WebDriver for browser: " + FrameWorkConstants.BROWSER + ". Error: " + e.getMessage();
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw e;
        }
    }


    public RemoteWebDriver initializeRemoteDriverWithBrowserChoice(String browser) {
        try {
            String url = FrameWorkConstants.PROXY_URL;

            if (url == null || url.isEmpty()) {
                String errorMessage = "Proxy URL is not specified or invalid.";
                CustomLogger.getLogger().error(errorMessage);
                Allure.step(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            browser = browser != null ? browser.trim().toLowerCase() : "";
            RemoteWebDriver remoteWebDriver;

            switch (browser) {
                case "edge":
                    String edgeMessage = "Initializing remote Edge WebDriver.";
                    CustomLogger.getLogger().info(edgeMessage);
                    Allure.step(edgeMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new Edge().createEdgeOptions(Options.getBrowserOptions()));
                    break;

                case "chrome":
                    String chromeMessage = "Initializing remote Chrome WebDriver.";
                    CustomLogger.getLogger().info(chromeMessage);
                    Allure.step(chromeMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new Chrome().createChromeOptions(Options.getBrowserOptions()));
                    break;

                case "firefox":
                    String firefoxMessage = "Initializing remote Firefox WebDriver.";
                    CustomLogger.getLogger().info(firefoxMessage);
                    Allure.step(firefoxMessage);
                    remoteWebDriver = new RemoteWebDriver(new URL(url), new FireFox().createFirefoxOptions(Options.getBrowserOptions()));
                    break;

//                case "safari":
//                    String safariMessage = "Initializing remote Safari WebDriver.";
//                    CustomLogger.getLogger().info(safariMessage);
//                    Allure.step(safariMessage);
//                    remoteWebDriver = new RemoteWebDriver(new URL(url), new SafariOptions());
//                    break;

                default:
                    String unsupportedMessage = "Unsupported or incorrect browser specified: " + browser;
                    CustomLogger.getLogger().error(unsupportedMessage);
                    Allure.step(unsupportedMessage);
                    throw new IllegalArgumentException(unsupportedMessage);
            }

            String successMessage = "Successfully initialized remote WebDriver for browser: " + browser;
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);
            return remoteWebDriver;

        } catch (MalformedURLException e) {
            String errorMessage = "Invalid Proxy URL: " + FrameWorkConstants.PROXY_URL;
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw new RuntimeException("Failed to create RemoteWebDriver due to malformed URL.", e);
        } catch (Exception e) {
            String errorMessage = "Failed to start remote WebDriver for browser: " + browser + ". Error: " + e.getMessage();
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw e;
        }
    }

    public WebDriver initializeDriverWithBrowserChoice(String browser) {
        try {
            if (browser == null || browser.trim().isEmpty()) {
                String errorMessage = "Browser name is null or empty.";
                CustomLogger.getLogger().error(errorMessage);
                Allure.step(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }

            String normalizedBrowser = browser.trim().toLowerCase();
            WebDriver webDriver;

            switch (normalizedBrowser) {
                case "edge":
                    String edgeMessage = "Initializing Edge WebDriver.";
                    CustomLogger.getLogger().info(edgeMessage);
                    Allure.step(edgeMessage);
                    webDriver = new Edge().initDriver();
                    break;

                case "chrome":
                    String chromeMessage = "Initializing Chrome WebDriver.";
                    CustomLogger.getLogger().info(chromeMessage);
                    Allure.step(chromeMessage);
                    webDriver = new Chrome().initDriver();
                    break;

                case "firefox":
                    String firefoxMessage = "Initializing Firefox WebDriver.";
                    CustomLogger.getLogger().info(firefoxMessage);
                    Allure.step(firefoxMessage);
                    webDriver = new FireFox().initDriver();
                    break;

//                case "safari":
//                    String safariMessage = "Initializing Safari WebDriver.";
//                    CustomLogger.getLogger().info(safariMessage);
//                    Allure.step(safariMessage);
//                    webDriver = new Safari().initDriver();
//                    break;

                default:
                    String unsupportedMessage = "Unsupported or incorrect browser specified: " + browser;
                    CustomLogger.getLogger().error(unsupportedMessage);
                    Allure.step(unsupportedMessage);
                    throw new IllegalArgumentException(unsupportedMessage);
            }

            String successMessage = "Successfully initialized WebDriver for browser: " + browser;
            CustomLogger.getLogger().info(successMessage);
            Allure.step(successMessage);
            return webDriver;

        } catch (IllegalArgumentException e) {
            CustomLogger.getLogger().error("Invalid browser name provided: {}", browser, e);
            Allure.step("Invalid browser name provided: " + browser);
            throw e;
        } catch (Exception e) {
            String errorMessage = "Failed to start WebDriver for browser: " + browser + ". Error: " + e.getMessage();
            CustomLogger.getLogger().error(errorMessage, e);
            Allure.step(errorMessage);
            throw new RuntimeException("Error occurred while starting WebDriver.", e);
        }
    }

}

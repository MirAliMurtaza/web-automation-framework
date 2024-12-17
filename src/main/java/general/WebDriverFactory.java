package general;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static config.ConfigProperties.*;

public class WebDriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<String> chosenBrowser = new ThreadLocal<>(); // New thread-local for browser

    static String device = Device;
    static Boolean headless = Boolean.valueOf(Headless);
    public static Actions action;
    public static Robot robot;

    // New method to set the browser for the current thread
    public static void setBrowser(String browser) {
        chosenBrowser.set(browser);
    }

    public static WebDriver getInstance() throws AWTException {
        // Use the thread-local browser if set, otherwise fallback to default from config
        String browserType = chosenBrowser.get() != null ? chosenBrowser.get().toLowerCase() : Browser.toLowerCase();

        WebDriverManager.chromedriver().setup();

        try {
            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (headless)
                        chromeOptions.addArguments("--headless");
                    if (device.equalsIgnoreCase("Windows"))
                        chromeOptions.addArguments("--start-maximized");
                    else
                        chromeOptions.addArguments("--start-fullscreen");
                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (headless)
                        firefoxOptions.addArguments("--headless");
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver());
                    break;

                case "safari":
                    driver.set(new SafariDriver());
                    break;

                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver.set(new InternetExplorerDriver());
                    break;

                default:
                    throw new IllegalStateException("Browser type " + browserType + " is not supported");
            }

            driver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            driver.get().manage().window().maximize();
            action = new Actions(driver.get());
            robot = new Robot();

            return driver.get();
        } catch (Exception e) {
            System.err.println("Failed to initialize the driver: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void finishDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
        chosenBrowser.remove(); // Clear browser after finishing
    }
}

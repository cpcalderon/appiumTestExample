package appiumTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.MyLogger;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class exampleWebAppTest {
    AppiumDriver<WebElement> driver;
    String USER_DIR = System.getProperty("user.dir");
    String TEXT_TO_SEARCH = "Appium webapp test";

    @BeforeClass
    public void beforeMethod() throws MalformedURLException {
        MyLogger.log.setLevel(Level.INFO);
        MyLogger.log.info("Starting before method");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Device name (can be random, but required)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "testDevice");

        // Platform, Android or iOS
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "android");

        // Browser to run
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");

        // Keep software keyboard closed to prevent intercepting taps on the screen
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        driver = new AppiumDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
    }

    @AfterClass
    public void afterMethod() {
        MyLogger.log.info("Starting after method");
        driver.quit();
    }

    @Test
    public void exampleWebAppTesting() throws InterruptedException {

        MyLogger.log.info("Test started");

        // Navigate to https://google.es
        MyLogger.log.info("Navigating to Google main page");
        driver.get("https://google.es");

        // Wait until the search box and search button are present and find them
        MyLogger.log.info("Waiting for the search button and text box");
        WebElement searchButton = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.className("Tg7LZd")));
        WebElement textBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        // Send the desired text and click search
        MyLogger.log.info("Sending text");
        textBox.sendKeys(TEXT_TO_SEARCH);
        MyLogger.log.info("Clicking search");
        searchButton.click();

        MyLogger.log.info("Searching types header");
        WebElement searchTypeHeader = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(("//*/div[@id='hdtb-msb']"))));

        // Wait for the new page to load
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        textBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        // Check new page is correctly displayed
        MyLogger.log.info("Checking new page");
        assertTrue("New page has been loaded correctly", textBox.getAttribute("value").equals(TEXT_TO_SEARCH) && searchTypeHeader.isDisplayed()
                && driver.findElementsByXPath("//*/div[@id='hdtb-msb']/div").size() > 5);

        MyLogger.log.info("Test successful");
    }
}

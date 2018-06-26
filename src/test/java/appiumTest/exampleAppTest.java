package appiumTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Constants;
import utils.MyLogger;
import utils.UtilsFunctions;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class exampleAppTest {
    private AppiumDriver<WebElement> driver;
    private final String PLATFORM_NAME = UtilsFunctions.propertiesLoader(Constants.USER_DIR+"/target/classes/test.properties").get("OS");
    private final String APPIUM_SERVER_URL = UtilsFunctions.propertiesLoader(Constants.USER_DIR+"/target/classes/test.properties").get("appiumServerURL");

    @BeforeClass
    public void beforeMethod() throws MalformedURLException {
        UtilsFunctions.setLoggerLevel("INFO");
        MyLogger.log.info("Starting before method");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // Device name (can be random, but required)
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "testDevice");

        // Platform, Android or iOS
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);

        // Keep software keyboard closed to prevent intercepting taps on the screen
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

        // APK location
        capabilities.setCapability(MobileCapabilityType.APP, Constants.USER_DIR + "/src/main/resources/app-debug.apk");
        capabilities.setCapability("noSign", true);

        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @AfterClass
    public void afterMethod() {
        MyLogger.log.info("Starting after method");
        driver.quit();
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void exampleAppTesting() throws InterruptedException {
        MyLogger.log.info("Test started");

        // Searches element by content-desc and checks if it's correctly pressed
        MyLogger.log.info("Searching radioButton2");
        MobileElement clickRadioButton2 = (MobileElement) driver.findElementByAccessibilityId("radioButton2");
        clickRadioButton2.click();

        MyLogger.log.info("Checking radioButton2 status");
        assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));

        // Searches element by XPath query and checks if it's correctly pressed
        // MobileElement clickRadioButton1 = (MobileElement) driver.findElementByXPath("//*/android.widget.RadioButton[contains(@resource-id,emergya.es.exampleapp:id/radioButton1)]");
        MyLogger.log.info("Searching radioButton1");
        MobileElement clickRadioButton1 = (MobileElement) new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/android.widget.RadioButton[contains(@resource-id,emergya.es.exampleapp:id/radioButton1)]")));
        clickRadioButton1.click();

        MyLogger.log.info("Checking radioButton1 status");
        assertTrue("Radio Button 1 is not pressed", clickRadioButton1.getAttribute("checked").equals("true"));

        // Searches element by ID, waits 5 seconds before giving up and checks if it's correctly pressed
        MyLogger.log.info("Searching text input box");
        MobileElement textInput = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/textInput"))));
        MyLogger.log.info("Writing text");
        textInput.sendKeys(Constants.TEXT_TO_SEARCH);

        MyLogger.log.info("Checking correct text has been written");
        assertTrue("Text hasn't been written", textInput.getText().equals(Constants.TEXT_TO_SEARCH));

        // Checks that the seek bar is present
        MyLogger.log.info("Searching seek bar");
        MobileElement seekBar = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/seekBar"))));

        MyLogger.log.info("Checking if seek bar is shown");
        assertTrue("Seek bar isn't shown", seekBar.isDisplayed());

        // Click and drag to position, waits inbetween so we can see it does something
        MyLogger.log.info("Moving seek bar");
        new TouchAction(driver).press(PointOption.point(70, 1530)).moveTo(PointOption.point(800, 1530)).release().perform();
        UtilsFunctions.waitSeconds(2);
        new TouchAction(driver).press(PointOption.point(800, 1530)).moveTo(PointOption.point(250, 1530)).release().perform();
        UtilsFunctions.waitSeconds(2);

        // Click on a position and check the action has been done
        MyLogger.log.info("Clicking on a point");
        new TouchAction(driver).press(PointOption.point(40, 700)).release().perform();
        MyLogger.log.info("Checking if click was successful");
        assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));

        MyLogger.log.info("Test successful");

    }
}

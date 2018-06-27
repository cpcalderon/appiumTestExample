package appiumTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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

public class exampleWebViewTest2 {
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

        // Application package and activity to run
        capabilities.setCapability(MobileCapabilityType.APP, Constants.USER_DIR + "/src/main/resources/cordova_demo.apk");

        capabilities.setCapability("noSign", true);

        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @AfterClass
    public void afterMethod() {
        MyLogger.log.info("Starting after method");
        driver.quit();
    }

    @Test
    public void cordovaTest() throws InterruptedException {

        MyLogger.log.info("Test started");

        UtilsFunctions.waitSeconds(2);
        MyLogger.log.info("Changing context to WebView");
        UtilsFunctions.changeToWebViewContext(driver);

        // Check the Device Plugin layout
        MyLogger.log.info("Checking layout of Device Plugin page");
        WebElement devicePluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h2='Device plugin']")));
        WebElement devicePluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(1)']")));

        assertTrue("Device plugin layout is not correct", devicePluginNext.isDisplayed() && devicePluginText.isDisplayed());

        // Go to next screen
        devicePluginNext.click();

        // Check the Camera Plugin layout
        MyLogger.log.info("Checking layout of Camera Plugin page");
        WebElement cameraPluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h3='Camera plugin']")));
        WebElement cameraPluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(2)']")));
        WebElement cameraPluginBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/img[@id='myImage']")));

        assertTrue("Camera plugin layout is not correct", cameraPluginBox.isDisplayed() && cameraPluginNext.isDisplayed() && cameraPluginText.isDisplayed());

        // Go to next screen
        cameraPluginNext.click();

        // Check the Contacts Plugin layout
        MyLogger.log.info("Checking layout of Contacts Plugin page");
        WebElement contactsPluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h3='Contacts plugin']")));
        WebElement contactsPluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(3)']")));
        WebElement contactsPluginTextBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/input[@id='contactSearch']")));
        WebElement contactsPluginPick = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='pickContact()']")));
        WebElement contactsPluginSearch = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/*/button[@onclick='readContacts()']")));

        assertTrue("Contacts plugin layout is not correct", contactsPluginNext.isDisplayed() && contactsPluginSearch.isDisplayed()
                && contactsPluginText.isDisplayed() && contactsPluginTextBox.isDisplayed() && contactsPluginPick.isDisplayed());

        // Write text in the textBox
        contactsPluginTextBox.sendKeys(Constants.TEXT_TO_SEARCH);
        assertTrue("Text hasn't been written correctly", contactsPluginTextBox.getAttribute("value").equals(Constants.TEXT_TO_SEARCH));

        // Search contacts
        contactsPluginSearch.click();
        WebElement contactsPluginFound = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[@id='contactsFound']")));
        assertTrue("Contact text hasn't popped in", contactsPluginFound.getText().equals(Constants.FOUND_CONTACTS));

        // Go to next screen
        contactsPluginNext.click();

        // Check the Globalization Plugin layout
        MyLogger.log.info("Checking layout of Globalization Plugin page");
        WebElement globalizationPluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h3='Globalization plugin']")));
        WebElement globalizationPluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(4)']")));

        assertTrue("Globalization plugin layout is not correct", globalizationPluginNext.isDisplayed() && globalizationPluginText.isDisplayed());

        // Go to next screen
        globalizationPluginNext.click();

        // Check the Vibration Plugin layout
        MyLogger.log.info("Checking layout of Vibration Plugin page");
        WebElement vibrationPluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h3='Vibration plugin']")));
        WebElement vibrationPluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(5)']")));
        WebElement vibrationPluginTextBox = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/input[@id='vibrationTime']")));
        WebElement vibrationPluginVibrate = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/*/button[@onclick='vibrate()']")));

        assertTrue("Vibration plugin layout is not correct", vibrationPluginNext.isDisplayed() && vibrationPluginText.isDisplayed()
                && vibrationPluginTextBox.isDisplayed() && vibrationPluginVibrate.isDisplayed());

        // Write in the vibration textBox and click on Vibrate!
        vibrationPluginTextBox.clear();
        vibrationPluginTextBox.sendKeys(Constants.HUNDRED);
        vibrationPluginVibrate.click();

        assertTrue("Text hasn't been written correctly", vibrationPluginTextBox.getAttribute("value").equals(Constants.HUNDRED));

        // Go to next screen
        vibrationPluginNext.click();

        // Check the Barcode Plugin layout
        MyLogger.log.info("Checking layout of Barcode Plugin page");
        WebElement barcodePluginText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[h3='Barcode plugin']")));
        WebElement barcodePluginNext = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='nextStep(0)']")));
        WebElement barcodePluginScan = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div/button[@onclick='scanBarcode()']")));

        assertTrue("Barcode plugin layout is not correct", barcodePluginNext.isDisplayed() && barcodePluginScan.isDisplayed() && barcodePluginText.isDisplayed());

        // Go back to first screen
        barcodePluginNext.click();

        MyLogger.log.info("Test successful");
    }
}

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

public class exampleWebviewTest {
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

        // Application package and activity to run, install the next app to continue, preferably in the preproduction environment
        // https://play.google.com/store/apps/details?id=es.educalab.edupills&hl=es_419
        capabilities.setCapability("appPackage", "es.educalab.edupills");
        capabilities.setCapability("appActivity", ".MainActivity");
        capabilities.setCapability("noSign", true);

        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @AfterClass
    public void afterMethod() {
        MyLogger.log.info("Starting after method");
        driver.quit();
    }

    @Test
    public void loginSuccessfulTest() throws InterruptedException {

        MyLogger.log.info("This test might fail if used in the production app, as WebView context is not shown. It does work in preproduction and staging environment application");

        MyLogger.log.info("Test started");

        UtilsFunctions.waitSeconds(2);
        MyLogger.log.info("Changing context to WebView");
        UtilsFunctions.changeToWebViewContext(driver);

        // Check the 'choose to login with' layout
        MyLogger.log.info("Checking layout of the login page");
        WebElement loginWith = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/ion-footer/button")));
        WebElement loginWithMail = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/page-login/*/button[*/ion-icon[@name='mail']]")));
        WebElement loginWithGoogle = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/page-login/*/button[*/ion-icon[contains(@name,'google')]]")));
        WebElement loginWithFacebook = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/page-login/*/button[*/ion-icon[contains(@name,'facebook')]]")));
        WebElement loginWithEmail = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/page-login/*/button[*/ion-icon[contains(@name,'twitter')]]")));

        assertTrue("There are missing items in the login page", loginWith.isDisplayed() && loginWithMail.isDisplayed()
                && loginWithGoogle.isDisplayed() && loginWithFacebook.isDisplayed() && loginWithEmail.isDisplayed()
                && driver.findElements(By.xpath("//*/ion-footer/button")).size() == 4);

        // Click the 'login with email' button
        MyLogger.log.info("Clicking login with email button");
        loginWithMail.click();

        // Find and write the username in the email field
        MyLogger.log.info("Writing email");
        WebElement emailText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/input[@type='email']")));
        emailText.sendKeys(Constants.EDUPILLS_USER);

        // Find and write the password in the password field
        MyLogger.log.info("Writing password");
        WebElement passwordText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/input[@type='password']")));
        passwordText.sendKeys(Constants.EDUPILLS_PASS);

        // Click on the login button
        MyLogger.log.info("Clicking login button");
        WebElement submitLogin = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/button[@type='submit']")));
        submitLogin.click();

        // Wait for the loading alert to disappear so we know the application is loaded
        MyLogger.log.info("Waiting for the new page to load");
        UtilsFunctions.waitSeconds(5);
        new WebDriverWait(driver, 30).until(ExpectedConditions.invisibilityOfElementLocated((By.xpath("//*/div[@class='loading-content']"))));
        WebElement searchMagnifier = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath("//*/button[*/ion-icon[@name='search']]")));
        WebElement profilePicture = new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath("//*/button[*/ion-icon[@name='contact']]")));

        // Check the layout is correct
        MyLogger.log.info("Checking main page layout");
        WebElement logoEdupills = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/img[@alt='Logo EduPills']")));
        WebElement menuList = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[@class='dashboard-menu-container']//ion-list/button")));
        WebElement aboutIcon = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[@class='about-icon-container']")));

        assertTrue("There are missing items in the main page", searchMagnifier.isDisplayed() && profilePicture.isDisplayed()
                && logoEdupills.isDisplayed() && aboutIcon.isDisplayed() && menuList.isDisplayed()
                && driver.findElements(By.xpath("//*/div[@class='dashboard-menu-container']//ion-list/button")).size() == 5);

        // Click on the profile picture
        MyLogger.log.info("Clicking the profile picture");
        profilePicture.click();

        // Check the layout
        MyLogger.log.info("Checking the profile page layout");
        WebElement profileImageContainer = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[@class='profile-container' and div[@class='image-container']]")));
        WebElement usernameInProfile = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='username']")));
        WebElement usermailInProfile = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='usermail']")));
        WebElement userLevel = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='level']")));
        WebElement userInsigniaMail = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='insignia']")));
        WebElement userPillCount = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='coursescount']")));
        WebElement userPillLabel = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/span[@class='courseslabel']")));
        WebElement userPillCategories = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/div[@class='category']")));

        assertTrue("There are missing items in the profile page", profileImageContainer.isDisplayed() && usernameInProfile.isDisplayed()
                && usermailInProfile.isDisplayed() && userLevel.isDisplayed() && userInsigniaMail.isDisplayed() && userPillCount.isDisplayed()
                && userPillLabel.isDisplayed() && userPillCategories.isDisplayed());

        // Logout
        MyLogger.log.info("Performing log out");
        WebElement logoutButton = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/ion-footer/button[span[@class='button-inner']]")));
        logoutButton.click();
        WebElement alertDialogLogout = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/ion-alert//*/button[2]")));
        alertDialogLogout.click();
        logoEdupills = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/img[@alt='Logo EduPills']")));

        assertTrue("Logout unsuccessful", logoEdupills.isDisplayed());

        MyLogger.log.info("Test successful");
    }
}

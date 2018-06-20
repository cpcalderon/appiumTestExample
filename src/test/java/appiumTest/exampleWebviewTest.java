package appiumTest;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import utils.UtilsFunctions;

public class exampleWebviewTest {
	AndroidDriver<WebElement> driver;
	String EDUPILLS_PASS = "A123456a";
	String EDUPILLS_USER = "pre170501@yopmail.com";

	@BeforeClass
	public void beforeMethod() throws MalformedURLException{

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Device name (can be random, but required)
		capabilities.setCapability("deviceName", "testDevice");

		// Platform, Android or iOS
		capabilities.setCapability("platformName", "android");

		// Application package and activity to run, install the next app to continue
		// https://play.google.com/store/apps/details?id=es.educalab.edupills&hl=es_419
		capabilities.setCapability("appPackage", "es.educalab.edupills");
		capabilities.setCapability("appActivity", ".MainActivity");
		capabilities.setCapability("noSign", true);

		// Keep software keyboard closed to prevent intercepting taps on the screen
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);

		driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	}

	@AfterClass
	public void afterMethod() {
		driver.quit();
	}

	@Test
	public void loginSuccessfulTest() throws InterruptedException{
		Set<String> contextNames = driver.getContextHandles();
		driver.context((String) contextNames.toArray()[1]); // set context to WEBVIEW

		// Find and click the login with email button
		WebElement emailButton = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/page-login/*/button[*/ion-icon[@name='mail']]")));
		emailButton.click();

		// Find and write the username in the email field
		WebElement emailText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/input[@type='email']")));
		emailText.sendKeys(EDUPILLS_USER);

		// Find and write the password in the password field
		WebElement passwordText = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/input[@type='password']")));
		passwordText.sendKeys(EDUPILLS_PASS);

		// Click on the login button
		WebElement submitLogin = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/button[@type='submit']")));
		submitLogin.click();

		// Wait for the profile picture to be clickable so we know the application is loaded
		new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.xpath("//*/button[*/ion-icon[@name='contact']]")));

		// Check the logo is shown
		WebElement logoEdupills = new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/img[@alt='Logo EduPills']")));
		assertTrue("The logo hasn't shown, login unsuccessful", logoEdupills.isDisplayed());

		// Wait so we can see the result on screen
		new UtilsFunctions().waitSeconds(2);
	}
}

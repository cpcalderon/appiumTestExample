package appiumTest;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class exampleWebAppTest {
	AndroidDriver<WebElement> driver;
	String USER_DIR = System.getProperty("user.dir");
	String TEXT_TO_SEARCH = "Appium webapp test";

	@BeforeClass
	public void beforeMethod() throws MalformedURLException{

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Device name (can be random, but required)
		capabilities.setCapability("deviceName", "testDevice");

		// Browser to run
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");

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
	public void exampleWebAppTesting() throws InterruptedException{
		// Navigate to https://google.es
		driver.get("https://google.es");

		// Wait until the search box is present and find it
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("q")));
		WebElement textBox = driver.findElementByName("q");

		// Send the desired text and press enter
		textBox.sendKeys(TEXT_TO_SEARCH);
		textBox.sendKeys(Keys.ENTER);

		// Wait for the new page to load by waiting for the search box, finding it again
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name("q")));
		textBox = driver.findElementByName("q");

		// Check actual text is the same as the one searched.
		assertTrue("Text is correct", textBox.getAttribute("value").equals(TEXT_TO_SEARCH));
	}
}

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xpath.axes.WalkingIteratorSorted;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class exampleWebAppTest {
	AndroidDriver<WebElement> driver;
	String USER_DIR = System.getProperty("user.dir");

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
		driver.get("https://google.es");
		WebElement textBox = driver.findElementByName("q");
		textBox.sendKeys("Appium webapp test");
		textBox.sendKeys(Keys.ENTER);
		waitSeconds(1);
		textBox = driver.findElementByName("q");
		assertTrue("Text is correct", textBox.getAttribute("value").equals("Appium webapp test"));
	}
	
	private void waitSeconds(int i) throws InterruptedException {
		Thread.sleep(i*1000);
	}
}

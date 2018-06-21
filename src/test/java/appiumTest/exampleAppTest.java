package appiumTest;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import utils.UtilsFunctions;

public class exampleAppTest {
	AndroidDriver<WebElement> driver;
	String USER_DIR = System.getProperty("user.dir");
	String TEXT_TO_SEARCH = "Probando aplicacion";

	@BeforeClass
	public void beforeMethod() throws MalformedURLException{

		DesiredCapabilities capabilities = new DesiredCapabilities();

		// Device name (can be random, but required)
		capabilities.setCapability("deviceName", "testDevice");
		// Platform, Android or iOS
		capabilities.setCapability("platformName", "android");
		// APK location
		capabilities.setCapability("app", USER_DIR+"/src/main/resources/app-debug.apk");
		// Application package and activity to run
		capabilities.setCapability("appPackage", "emergya.es.exampleapp");
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

	@SuppressWarnings("rawtypes")
	@Test
	public void exampleAppTesting() throws InterruptedException {
		
		// Searches element by content-desc and checks if it's correctly pressed
		MobileElement clickRadioButton2 = (MobileElement) driver.findElementByAccessibilityId("radioButton2");
		clickRadioButton2.click();
		
		assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));

		// Searches element by XPath query and checks if it's correctly pressed
		// MobileElement clickRadioButton1 = (MobileElement) driver.findElementByXPath("//*/android.widget.RadioButton[contains(@resource-id,emergya.es.exampleapp:id/radioButton1)]");
		MobileElement clickRadioButton1 = (MobileElement) new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*/android.widget.RadioButton[contains(@resource-id,emergya.es.exampleapp:id/radioButton1)]")));
		clickRadioButton1.click();
		
		assertTrue("Radio Button 1 is not pressed", clickRadioButton1.getAttribute("checked").equals("true"));

		// Searches element by ID, waits 5 seconds before giving up and checks if it's correctly pressed
		MobileElement textInput = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/textInput"))));
		textInput.sendKeys(TEXT_TO_SEARCH);
		
		assertTrue("Text hasn't been written", textInput.getText().equals(TEXT_TO_SEARCH));

		// Checks that the seek bar is present
		MobileElement seekBar = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/seekBar"))));
		
		assertTrue("Seek bar isn't shown", seekBar.isDisplayed());
		
		// Click and drag to position, waits inbetween so we can see it does something
		new TouchAction(driver).press(PointOption.point(70, 1530)).moveTo(PointOption.point(800, 1530)).release().perform();
		new UtilsFunctions().waitSeconds(2);
		new TouchAction(driver).press(PointOption.point(800,1530)).moveTo(PointOption.point(250, 1530)).release().perform();
		new UtilsFunctions().waitSeconds(2);
		// Click on a position and check the action has been done
		new TouchAction(driver).press(PointOption.point(40,700)).release().perform();
		
		assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));

	}
}

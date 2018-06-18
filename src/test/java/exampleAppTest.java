import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;


import org.openqa.selenium.By;
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

public class exampleAppTest {
	AndroidDriver<WebElement> driver;
	String USER_DIR = System.getProperty("user.dir");

	@BeforeClass
	public void beforeMethod() throws MalformedURLException{

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

		capabilities.setCapability("deviceName", "testDevice");
		capabilities.setCapability("platformName", "android");		
		capabilities.setCapability("app", USER_DIR+"/src/main/resources/app-debug.apk");
		capabilities.setCapability("appPackage", "emergya.es.exampleapp");
		capabilities.setCapability("appActivity", ".MainActivity");
		capabilities.setCapability("noSign", true);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("resetKeyboard", true);

		driver = new AndroidDriver<WebElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	}

	@AfterClass
	public void afterMethod() {
		driver.quit();
	}
	
	@Test
	public void exampleAppTesting() throws InterruptedException {
		// Searches element by content-desc
		MobileElement clickRadioButton2 = (MobileElement) driver.findElementByAccessibilityId("radioButton2");
		clickRadioButton2.click();
		assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));
		// Searches element by XPath query
		MobileElement clickRadioButton1 = (MobileElement) driver.findElementByXPath("//*/android.widget.RadioButton[contains(@resource-id,emergya.es.exampleapp:id/radioButton1)]");
		clickRadioButton1.click();
		assertTrue("Radio Button 1 is not pressed", clickRadioButton1.getAttribute("checked").equals("true"));
		// Searches element by ID, waits 5 seconds before giving up
		MobileElement textInput = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/textInput"))));
		textInput.sendKeys("Probando aplicacion");
		assertTrue("Text hasn't been written", textInput.getText().equals("Probando aplicacion"));
		MobileElement seekBar = (MobileElement) (new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated((By.id("emergya.es.exampleapp:id/seekBar"))));
		// Click and drag to position
		new TouchAction(driver).press(70,1530).moveTo(800, 1530).release().perform();
		waitSeconds(1);
		new TouchAction(driver).press(800,1530).moveTo(250, 1530).release().perform();
		waitSeconds(1);
		// Click on a position
		new TouchAction(driver).press(40,700).release().perform();
		waitSeconds(1);
		assertTrue("Radio Button 2 is not pressed", clickRadioButton2.getAttribute("checked").equals("true"));

	}

	private void waitSeconds(int i) throws InterruptedException {
		Thread.sleep(i*1000);
	}
}

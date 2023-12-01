package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import selenium4.com.driver.DriverManager;
import selenium4.com.common.BaseTest;
import selenium4.com.helpers.ScreenRecorderHelpers;
import selenium4.com.helpers.ScreenshotHelpers;
import selenium4.com.utils.LogUtils;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class LoggingTest extends BaseTest {

	private ScreenRecorderHelpers screenRecorder;
	//@Test
	public void testElementNotInteractableExceptionHandling_usingSeleniumClick() throws Exception {
		getURL("https://login.yahoo.com/");
		WebElement chkSignIn = waitForElementPresent(By.xpath("//label[@for='persistent']//preceding-sibling::input"));
		ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), "testElementNotInteractableExceptionHandling_usingSeleniumClick");
		ScreenshotHelpers.getScreenshotFile("---testElementNotInteractableExceptionHandling_usingSeleniumClick123");
		LogUtils.info(" --- click element and get org.openqa.selenium.ElementNotInteractableException: element not interactable");
		chkSignIn.click();
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
	}

	@Test
	public void testElementNotInteractableExceptionHandling_usingJavascriptClick() throws Exception {
		getURL("https://login.yahoo.com/");

		screenRecorder = new ScreenRecorderHelpers();
		screenRecorder.startRecording("testElementNotInteractableExceptionHandling_usingJavascriptClick");
		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		clickElementWithJs(bySignIn);
		System.out.println("--Checkbox is clicked");
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(2);

		clickElementWithJs(bySignIn);
		System.out.println("--Checkbox is clicked");
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(5);
		screenRecorder.stopRecording(true);
	}

	// @Test
	public void testElementNotInteractableExceptionHandling_sendKeys() throws Exception {
		getURL("https://www.letskodeit.com/practice");

		clickElement(By.id("hide-textbox"));
		Thread.sleep(1000);
		// Set text in text box with sendKeys
		getWebElement(By.id("displayed-text")).sendKeys("enter hidden input");
		// org.openqa.selenium.ElementNotInteractableException: element not interactable
	}

	//@Test
	public void testElementNotInteractableExceptionHandling_usingJavascriptValue() throws Exception {
		getURL("https://www.letskodeit.com/practice");

		clickElement(By.id("hide-textbox"));
		Thread.sleep(1000);
		// Set text in text box with sendKeys
		// getWebElement(By.id("displayed-text")).sendKeys("enter hidden input");
		// Set text in text box with Javascript
		setTextWithJs("displayed-text", "enter hidden input");
		clickElement(By.id("show-textbox"));
		Thread.sleep(1000);
	}
}

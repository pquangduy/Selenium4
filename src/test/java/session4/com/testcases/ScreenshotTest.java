package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import selenium4.com.driver.DriverManager;
import selenium4.com.common.BaseTest;
import selenium4.com.helpers.ScreenshotHelpers;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class ScreenshotTest extends BaseTest {

	@Test
	public void testElementNotInteractableExceptionHandling_usingSeleniumClick() throws Exception {
		getURL("https://login.yahoo.com/");

		ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), "screenshotUsingSelenium_BeforeClick");
		ScreenshotHelpers.getScreenshotFile("screenshotUsingRobotClass_BeforeClick");
		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		clickElementWithJs(bySignIn);
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(1);
		ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), "screenshotUsingSelenium_AfterClick");
		ScreenshotHelpers.getScreenshotFile("screenshotUsingRobotClass_AfterClick");
	}
}

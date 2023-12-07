package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.helpers.ScreenRecorderHelpers;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class ScreenRecorderTest extends BaseTest {

	private ScreenRecorderHelpers screenRecorder;

	@Test
	public void testElementNotInteractableExceptionHandling_usingJavascriptClick() throws Exception {
		getURL("https://login.yahoo.com/");

		screenRecorder = new ScreenRecorderHelpers();
		screenRecorder.startRecording("testElementNotInteractableExceptionHandling_usingJavascriptClick");
		sleep(2);

		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		clickElementWithJs(bySignIn);
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(2);

		clickElementWithJs(bySignIn);
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(2);

		screenRecorder.stopRecording(true);
	}
}

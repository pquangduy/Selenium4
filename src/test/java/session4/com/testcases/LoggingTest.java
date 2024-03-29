package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.utils.LogUtils;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class LoggingTest extends BaseTest {

	@Test
	public void testElementNotInteractableExceptionHandling_usingSeleniumClick() throws Exception {
		LogUtils.info("--- Test case: testElementNotInteractableExceptionHandling_usingSeleniumClick");
		getURL("https://login.yahoo.com/");
		
		WebElement chkSignIn = waitForElementPresent(By.xpath("//label[@for='persistent']//preceding-sibling::input"));
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
		LogUtils.info("--- Test case: testElementNotInteractableExceptionHandling_usingJavascriptClick");
		getURL("https://login.yahoo.com/");

		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		clickElementWithJs(bySignIn);
		System.out.println("--Checkbox is clicked");
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(1);
	}
	
	@Test
	public void testSkipTestCase() throws Exception {
		LogUtils.warn("Test case: testSkipTestCase is skipped");
		throw new SkipException("Test voluntarily skipped.");
	}
}

package session2.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;

public class ElementNotInteractableExceptionHandlingTest extends BaseTest {

	// @Test
	public void testElementNotInteractableExceptionHandling_usingSeleniumClick() throws Exception {
		getURL("https://login.yahoo.com/");

		WebElement chkSignIn = waitForElementPresent(By.xpath("//label[@for='persistent']//preceding-sibling::input"));
		// checkbox SignIn is not displayed in DOM => chkSignIn.isDisplayed() return
		// false
		// Selenium click
		chkSignIn.click();
		// org.openqa.selenium.ElementNotInteractableException: element not interactable
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
	}

	// @Test
	public void testElementNotInteractableExceptionHandling_usingJavascriptClick() throws Exception {
		getURL("https://login.yahoo.com/");

		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		// checkbox SignIn is not displayed in DOM => chkSignIn.isDisplayed() return
		// false
		// Selenium click
		// m.click();
		// JavascriptExecutor to click element
		clickElementWithJs(bySignIn);
		System.out.println("--Checkbox is clicked");
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		Thread.sleep(1000);

		clickElementWithJs(bySignIn);
		System.out.println("--Checkbox is clicked");
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		Thread.sleep(1000);
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

	@Test
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

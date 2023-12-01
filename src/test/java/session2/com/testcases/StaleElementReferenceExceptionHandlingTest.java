package session2.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;

public class StaleElementReferenceExceptionHandlingTest extends BaseTest {

	// @Test
	public void testStaleElementReferenceException() throws Exception {
		getURL("https://google.com");
		WebElement ele = waitForElementVisible(By.xpath("//textarea[@id='APjFqb']"));
		System.out.println("--- Before refresh");
		ele.sendKeys("Before Refresh");
		Thread.sleep(1000);
		// Refresh the web page
		reloadPage();
		ele.sendKeys("After Refresh");
		// org.openqa.selenium.StaleElementReferenceException
	}

	// @Test
	public void testStaleElementReferenceExceptionHandling_usingTryCatch() throws Exception {
		By searchInput = By.xpath("//textarea[@id='APjFqb']");
		String expectedInputAfterRefresh = "After Refresh";

		getURL("https://google.com");
		WebElement ele = waitForElementVisible(searchInput);
		System.out.println("--- Before refresh");
		ele.sendKeys("Before Refresh");
		Thread.sleep(1000);
		// Refresh the web page
		reloadPage();
		// ele.sendKeys(expectedInputAfterRefresh);
		try {
			ele.sendKeys(expectedInputAfterRefresh);
		} catch (StaleElementReferenceException e) {
			ele = waitForElementVisible(searchInput);
			ele.sendKeys(expectedInputAfterRefresh);
			System.out.println("The string entered from catch block is - " + ele.getAttribute("value"));
			Assert.assertEquals(ele.getAttribute("value"), expectedInputAfterRefresh);
			Thread.sleep(1000);
		}
	}

	@Test
	public void testStaleElementReferenceExceptionHandling_usingExplicitWait() throws Exception {
		By searchInput = By.xpath("//textarea[@id='APjFqb']");
		String expectedInputAfterRefresh = "After Refresh";

		getURL("https://google.com");
		WebElement ele = waitForElementVisible(searchInput);
		System.out.println("--- Before refresh");
		ele.sendKeys("Before Refresh");
		Thread.sleep(1000);
		// Refresh the web page
		reloadPage();
		ele = waitForElementPresent(searchInput);
		ele.sendKeys(expectedInputAfterRefresh);
		System.out.println("The string entered from Search Input is - " + ele.getAttribute("value"));
		Assert.assertEquals(ele.getAttribute("value"), expectedInputAfterRefresh);
		Thread.sleep(1000);
	}
}

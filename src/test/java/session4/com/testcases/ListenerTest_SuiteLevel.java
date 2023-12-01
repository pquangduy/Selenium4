package session4.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;
import selenium4.com.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ListenerTest_SuiteLevel extends BaseTest {

	@Test
	public void testStaleElementReferenceExceptionHandling_usingTryCatch() throws Exception {
		By searchInput = By.xpath("//textarea[@id='APjFqb']");
		String expectedInputAfterRefresh = "After Refresh";

		getURL("https://google.com");
		WebElement ele = waitForElementVisible(searchInput);
		System.out.println("--- Before refresh");
		ele.sendKeys("Before Refresh");
		sleep(1);
		// Refresh the web page
		reloadPage();
		try {
			ele.sendKeys(expectedInputAfterRefresh);
		} catch (StaleElementReferenceException e) {
			ele = waitForElementVisible(searchInput);
			ele.sendKeys(expectedInputAfterRefresh);
			System.out.println("The string entered from catch block is - " + ele.getAttribute("value"));
			Assert.assertEquals(ele.getAttribute("value"), expectedInputAfterRefresh);
			sleep(1);
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
		sleep(1);
		// Refresh the web page
		reloadPage();
		ele = waitForElementPresent(searchInput);
		ele.sendKeys(expectedInputAfterRefresh);
		System.out.println("The string entered from Search Input is - " + ele.getAttribute("value"));
		Assert.assertEquals(ele.getAttribute("value"), expectedInputAfterRefresh);
		sleep(1);
	}
	
	@Test
	public void testStaleElementReferenceExceptionHandling_skipTestCase() throws Exception {
		throw new SkipException("Test voluntarily skipped.");
	}
}

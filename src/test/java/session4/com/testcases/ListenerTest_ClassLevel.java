package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class ListenerTest_ClassLevel extends BaseTest {

	@Test
	public void testTestCase_Fail() throws Exception {
		getURL("https://login.yahoo.com/");
		WebElement chkSignIn = waitForElementPresent(By.xpath("//label[@for='persistent']//preceding-sibling::input"));
		chkSignIn.click();
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
	}

	@Test
	public void testTestCase_Pass() throws Exception {
		getURL("https://login.yahoo.com/");

		By bySignIn = By.xpath("//label[@for='persistent']//preceding-sibling::input");
		WebElement chkSignIn = waitForElementPresent(bySignIn);
		clickElementWithJs(bySignIn);
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
		sleep(1);

		clickElementWithJs(bySignIn);
		if (chkSignIn.isSelected()) {
			System.out.println("Checkbox is checked");
		} else {
			System.out.println("Checkbox is not checked");
		}
	}

	@Test
	public void testTestCase_Skip() throws Exception {
		throw new SkipException("Test voluntarily skipped.");
	}
}

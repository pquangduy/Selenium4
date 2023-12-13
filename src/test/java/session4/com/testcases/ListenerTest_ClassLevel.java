package session4.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import selenium4.com.common.BaseTest;
import static selenium4.com.helpers.WebElementsHelpers.*;

public class ListenerTest_ClassLevel extends BaseTest {

	@Test(priority=2, description="verifying clicking check box using Selenium")
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Case Description: Verify clicking checkbox using Selenium")
	@Story("Story: CORE-101")
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

	@Test(priority=1, description="verifying clicking check box using Javascript")
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Case Description: Verifying clicking check box using Javascript")
	@Story("Story: CORE-203")
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

	@Test(priority=3, description="verifying skip test case")
	@Severity(SeverityLevel.TRIVIAL)
	@Description("Test Case Description: Verifying skip test case")
	@Story("Story: CORE-203")
	public void testTestCase_Skip() throws Exception {
		throw new SkipException("Test voluntarily skipped.");
	}
}

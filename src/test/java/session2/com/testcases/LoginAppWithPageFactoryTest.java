package session2.com.testcases;

import org.testng.annotations.Test;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import selenium4.com.common.BaseTest;
import session2.com.pages.LoginPage;
import session2.com.pages.HomePage;

import static selenium4.com.helpers.WebElementsHelpers.getURL;

import org.testng.Assert;

@Epic("Regression Test")
@Feature("Login App Test")

public class LoginAppWithPageFactoryTest extends BaseTest {

	private String userName = "mngr529240";
	private String password = "mehuqyh";
	LoginPage objLogin;
	HomePage objHomePage;

	/**
	 * This test case will login in http://demo.guru99.com/V4/ Verify login page
	 * title as guru99 bank Login to application Verify the home page using Home
	 * Page message
	 */

	@Test(priority = 0)
	public void testHomePageAppearCorrectWithPageFactory() {
		String expectedHomePageTitle = "manger id : mngr529240";
		String expectedLoginPageTitle = "guru99 bank";

		getURL("http://demo.guru99.com/V4/");

		// Verify login page title
		objLogin = new LoginPage();
		Assert.assertTrue(objLogin.getLoginTitle().toLowerCase().contains(expectedLoginPageTitle));
		// login to application
		objLogin.loginToApplication(userName, password);
		// go the next page
		objHomePage = new HomePage();
		// Verify home page
		Assert.assertTrue(objHomePage.getHomePageUserName().toLowerCase().contains(expectedHomePageTitle));
	}

	@Test(priority = 0)
	public void testLoginPageAppearCorrectWithPageFactory() throws Exception {
		String expectedLoginPageTitle = "guru99 bank";

		getURL("http://demo.guru99.com/V4/");

		// Verify login page title
		objLogin = new LoginPage();
		Assert.assertTrue(objLogin.getLoginTitle().toLowerCase().contains(expectedLoginPageTitle));
		Thread.sleep(1000);
	}

}

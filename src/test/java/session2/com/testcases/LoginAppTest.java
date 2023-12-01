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

public class LoginAppTest extends BaseTest {

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
	public void testHomePageAppearCorrect() {
		String expectedHomePageTitle = "manger id : mngr529240";
		String expectedLoginPageTitle = "guru99 bank";

		getURL("http://demo.guru99.com/V4/");

		// Verify login page title
		objLogin = new LoginPage();
		Assert.assertTrue(objLogin.getLoginTitle().toLowerCase().contains(expectedLoginPageTitle));
		// login to application
		objHomePage = objLogin.loginToApplication(userName, password);
		// Verify home page
		Assert.assertTrue(objHomePage.getHomePageUserName().toLowerCase().contains(expectedHomePageTitle));
	}

	//@Test(priority = 0)
	public void testLoginPageAppearCorrectAfterLogout_WithPageChaining() {
		String expectedLoginPageTitle = "guru99 bank";

		getURL("http://demo.guru99.com/V4/");

		// Verify login page title after login to application and then logout
		objLogin = new LoginPage();
		String loginTitle = objLogin.loginToApplication(userName, password).clickLogout().clickOkOnAlertAppear()
				.getLoginTitle();
		System.out.print("Login Page title after Logout : " + loginTitle);
		Assert.assertTrue(loginTitle.toLowerCase().contains(expectedLoginPageTitle));
	}

	//@Test(priority = 0)
	public void testLoginPageAppearCorrectAfterLogout_WithoutPageChaining() {
		String expectedLoginPageTitle = "guru99 bank";

		getURL("http://demo.guru99.com/V4/");

		// Verify login page title after login to application and then logout
		objLogin = new LoginPage();
		objHomePage = new HomePage();
		objLogin.loginToApplication(userName, password);
		objHomePage.clickLogout();
		objLogin.clickOkOnAlertAppear();
		String loginTitle = objLogin.getLoginTitle();
		System.out.print("Login Page title after Logout : " + loginTitle);
		Assert.assertTrue(loginTitle.toLowerCase().contains(expectedLoginPageTitle));
	}
}

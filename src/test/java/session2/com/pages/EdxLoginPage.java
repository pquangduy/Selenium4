package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;

public class EdxLoginPage {

	private By inputUserName = By.id("emailOrUsername");
	private By inputPassword = By.id("password");
	private By buttonSignIn = By.id("sign-in");

	public EdxLoginPage() {
	}

	public String getEdxLoginPageTitle() {
		return getPageTitle();
	}

	public void setUserName(String strUserName) {
		setText(inputUserName, strUserName);
	}

	public void setPassword(String strPassword) {
		setText(inputPassword, strPassword);
	}

	public void clickSignIn() {
		clickElement(buttonSignIn);
	}

	public EdxHomePage loginToApplication(String strUserName, String strPasword) {
		this.setUserName(strUserName);
		this.setPassword(strPasword);
		this.clickSignIn();

		return new EdxHomePage();
	}
}

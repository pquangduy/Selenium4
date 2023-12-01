package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;

import selenium4.com.driver.DriverManager;
import selenium4.com.utils.LogUtils;

public class LoginPage {

	public By inputUserName = By.name("uid");
	public By inputPassword = By.name("password");
	public By textTitle = By.className("barone");
	public By buttonLogin = By.name("btnLogin");

	public LoginPage() {
	}

	public void setUserName(String strUserName) {
		setText(inputUserName, strUserName);
	}

	public void setPassword(String strPassword) {
		setText(inputPassword, strPassword);
	}

	public void clickLogin() {
		clickElement(buttonLogin);
	}

	public String getLoginTitle() {
		return getTextElement(textTitle);
	}

	public LoginPage clickOkOnAlertAppear() {
		if (verifyAlertPresent(10)) {
			DriverManager.getDriver().switchTo().alert().accept();
		} else {
			LogUtils.info("No Alert disappear ");
		}

		return this;
	}

	public HomePage loginToApplication(String strUserName, String strPasword) {
		this.setUserName(strUserName);
		this.setPassword(strPasword);
		this.clickLogin();

		return new HomePage();
	}
}

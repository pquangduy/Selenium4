package session2.com.pagefactory;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium4.com.driver.DriverManager;

public class LoginPage {

	@FindBy(name="uid")
    WebElement inputUserName;
	@FindBy(name="password")
    WebElement inputPassword;
	@FindBy(className="barone")
    WebElement textTitle;
	@FindBy(name="btnLogin")
    WebElement buttonLogin;

	public LoginPage() {
		//This initElements method will create all WebElements
        PageFactory.initElements(DriverManager.getDriver(), this);
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

	/**
	 * 
	 * This POM method will be exposed in test case to login in the application
	 * 
	 * @param strUserName
	 * @param strPasword
	 * @return
	 */
	public void loginToApplication(String strUserName, String strPasword) {
		this.setUserName(strUserName);
		this.setPassword(strPasword);
		this.clickLogin();
	}
}

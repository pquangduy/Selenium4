package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;

public class EdxIndexPage {

	private By buttonSignIn = By.xpath("//a[contains(text(),'Sign in')]");

	public EdxIndexPage() {
	}

	public String getEdxIndexPageTitle() {
		return getPageTitle();
	}

	public EdxLoginPage clickSignIn() {
		clickElement(buttonSignIn);
		
		return new EdxLoginPage();
	}

}

package session2.com.pagefactory;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium4.com.driver.DriverManager;

public class HomePage {

	@FindBy(xpath = "//table//tr[@class='heading3']")
	WebElement textHomePageUserName;

	public HomePage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	public String getHomePageUserName() {
		return getTextElement(textHomePageUserName);
	}
}

package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;

import selenium4.com.driver.DriverManager;

public class EdxHomePage {

	private By btnExploreCourses = By.xpath("//a[contains(text(),'Explore courses')]");

	public String getEdxHomePageTitle() {
		return getPageTitle();
	}
	
	public EdxSearchPage clickExploreCourses() {
		clickElement(btnExploreCourses);
		
		return new EdxSearchPage();
	}
}

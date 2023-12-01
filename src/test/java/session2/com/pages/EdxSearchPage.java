package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import org.openqa.selenium.By;

public class EdxSearchPage {

	private By inputSearch = By.xpath("//main[@id='main-content']//div[@id='search-landing-search-input']/input");
	private By btnSearch = By.id("search-landing-search-submit");
	
	public String getEdxSearchPageTitle() {
		return getPageTitle();
	}

	public void setSearchBox(String strCourse) {
		setTextWithActions(inputSearch, strCourse);
		setText(inputSearch, strCourse);
	}

	public EdxSearchResultPage clickSearch() {
		//clickElement(btnSearch);
		clickElementWithJs(btnSearch);
		
		return new EdxSearchResultPage();
	}
}

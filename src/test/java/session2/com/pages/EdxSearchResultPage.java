package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EdxSearchResultPage {

	private String searchBase = "//main[@name='main-content']//div[contains(@class,'container-mw-lg container-fluid')]/div[contains(@class,'card-container')]";
	private By textSearchResult = By.xpath(searchBase);
	private By courseCard = By.className("pgn__card-header-content");
	
	public String getEdxSearchResultPageTitle() {
		return getPageTitle();
	}

	public int getSearchResult() {
		List<WebElement> l = getWebElements(textSearchResult);
		
		return l.size();
	}

	public CourseDetailsPage selectCourse(String courseName) {
		List<WebElement> l = waitForNumberOfElementsToBeMoreThan(courseCard, 1);
		System.out.println("Course size is : " + l.size());
		List<WebElement> l4 = waitForNumberOfElementsToBeMoreThan(By.className("pgn__card-header-content"), 1);
		System.out.println("Course size l4 is : " + l4.size());
		WebElement result = null;
		sleep(5);
		for(WebElement e : l4){
			System.out.println("Course name l4 is : " + getTextElement(e));
			if (getTextElement(e).contains(courseName)) {
				System.out.println("Course name l4 is : " + getTextElement(e));
				logConsole(" --- Found ");
				result = e;
				break;
			}
		}
		result.click();
		
		return new CourseDetailsPage();
	}
}

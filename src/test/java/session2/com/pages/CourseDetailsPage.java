package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CourseDetailsPage {

	private By courseTitle = By.xpath("//main[@id='main-content']//div[@class='row no-gutters']/div/h1");
	private By courseDesc = By.xpath("//main[@id='main-content']//div[@class='row no-gutters']//div[@class='p']");
	private By showMore = By.xpath("//button[text()='Show more']");
	private By courseSummary = By.xpath("//h1[text()=\"What you'll learn\"]//parent::div/following-sibling::div/div");
	private By instructors = By.xpath("//h1[text()='About the instructors']/following-sibling::div/a");
	private By enrollBtn = By.xpath("//button[@label='Enroll']");
	private By continueBtn = By.xpath("//button[@id='track_selection_audit']/span");
	
	public String getCourseDetailsPageTitle() {
		return getPageTitle();
	}

	public Map<String, Object> getCourseDetails(String strCourse) {
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("Title", getTextElement(courseTitle));		
		prefs.put("Description", getTextElement(courseDesc));
		//clickElement(showMore);
		//sleep(1);
		scrollToElementToTop(courseSummary);
		//sleep(1);
		String summary = getTextElement(courseSummary);
		prefs.put("Summary", Arrays.asList(summary.split("\n")));
		List<WebElement> l4 = waitForNumberOfElementsToBeMoreThan(instructors, 0);
		int s = l4.size();
		System.out.println("Instructors size is : " + s);
		List<String> arrlist = new ArrayList<String>();
		for(WebElement e : l4){
			System.out.println("-- Course name l4 is : " + getTextElement(e));
			arrlist.add(getTextElement(e));
		}
		prefs.put("Instructors", arrlist);
		sleep(1);
		System.out.println("----------------- : ");
		for (Map.Entry<String, Object> entry : prefs.entrySet()) {
	        System.out.println(entry.getKey() + ":" + entry.getValue());
	        if(entry.getKey().equals("Instructors")) {
	        	List<?> l1 = (List<?>) entry.getValue();
	        	for(int i=0; i<l1.size(); i++) {
	        		String sa = (String) l1.get(i);
	        		logConsole("---Instruc: " + sa);
	        	}
	        }
	    }
		return prefs;
	}

	public void clickEnroll() {
		List<WebElement> l = waitForNumberOfElementsToBeMoreThan(enrollBtn, 0);
		l.get(0).click();
	}
	
	public EdxCourseContentPage clickContinue() {
		clickElement(continueBtn);
		
		return new EdxCourseContentPage();
	}
}

package session2.com.pages;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class EdxCourseContentPage {

	private By expandAllBtn = By.xpath("//button[text()='Expand all']");
	private String strItem = "//ol[@id='courseHome-outline']/li";
	private By courseItemHeader = By.xpath("//ol[@id='courseHome-outline']//div[@class='collapsible-trigger']//span[@class='align-middle']");
	
	public String getEdxCourseContentPageTitle() {
		return getPageTitle();
	}

	public void clickExpandAll() {
		clickElement(expandAllBtn);
	}

	public Map<String, Object> getCourseContent() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		List<WebElement> l = waitForNumberOfElementsToBeMoreThan(By.xpath(strItem), 1);
		int n = l.size();
		ArrayList<String>[] arrlist = new ArrayList[n];
		for(int i=1; i<=n; i++) {
			arrlist[i-1] = new ArrayList<String>();
			logConsole("---------------------------------------------------");
			String strHeader = strItem + "[" + i + "]//div[@class='collapsible-trigger']//span[@class='align-middle']";
			arrlist[i-1].add(getTextElement(By.xpath(strHeader)));
			String strBody = strItem + "[" + i + "]//div[@class='collapsible-body']//span[@class='align-middle']/a";
			List<WebElement> body = waitForNumberOfElementsToBeMoreThan(By.xpath(strBody), 0);
			for(WebElement e : body) {
				arrlist[i-1].add(getTextElement(e));
			}
		}
		prefs.put("CourseContent", arrlist);
		return prefs;
	}

	public Map<String, Object> getCourseContent1() {
		Map<String, Object> prefs = new HashMap<String, Object>();
		List<WebElement> l = waitForNumberOfElementsToBeMoreThan(By.xpath(strItem), 1);
		int n = l.size();
		String[][] arr = new String[n][];
		for(int i=1; i<=n; i++) {
			logConsole("---------------------------------------------------");
			String strHeader = strItem + "[" + i + "]//div[@class='collapsible-trigger']//span[@class='align-middle']";
			String strBody = strItem + "[" + i + "]//div[@class='collapsible-body']//span[@class='align-middle']/a";
			List<WebElement> body = waitForNumberOfElementsToBeMoreThan(By.xpath(strBody), 0);
			arr[i-1] = new String[body.size() + 1];
			arr[i-1][0] = getTextElement(By.xpath(strHeader));
			logConsole("---Header: " + arr[i-1][0]);
			for(int j=1; j<=body.size(); j++) {
				arr[i-1][j] = getTextElement(body.get(j-1)) + "\n" + getAttributeElement(body.get(j-1), "href");
				logConsole("---Body: " + arr[i-1][j]);
			}
		}
		prefs.put("CourseContent", arr);
		return prefs;
	}

}

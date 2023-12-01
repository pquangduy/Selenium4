package session2.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.driver.DriverManager;

public class SeleniumWaitsTest extends BaseTest {

	// @Test
	public void testImplicitWait_WithDefaultValue() throws Exception {
		long defaultValue = 0;

		DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultValue));
		getURL("https://www.selenium.dev/selenium/web/dynamic.html");
		clickElement(By.id("adder"));
		WebElement boxElement = DriverManager.getDriver().findElement(By.id("box0"));
		// org.openqa.selenium.NoSuchElementException: no such element: Unable to locate
		// element
		System.out.println("Background color is : " + boxElement.getCssValue("background-color"));
	}

	@Test
	public void testImplicitWait_WithCustomValue() throws Exception {
		long customValue = 10;
		String expectedBgrColor = "rgba(255, 0, 0, 1)";

		DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(customValue));
		getURL("https://www.selenium.dev/selenium/web/dynamic.html");
		clickElement(By.id("adder"));
		WebElement boxElement = DriverManager.getDriver().findElement(By.id("box0"));
		System.out.println("Background color is : " + boxElement.getCssValue("background-color"));
		Assert.assertEquals(boxElement.getCssValue("background-color").toString(), expectedBgrColor);
		System.out.println(" -- Attr Background color is : " + boxElement.getAttribute("style"));
		System.out.println(" -- Dom Attr Background color is : " + boxElement.getDomAttribute("style"));
		System.out.println(" -- Dom Prop Background color is : " + boxElement.getDomProperty("style"));
		Thread.sleep(1000);

		setDomAttribute(boxElement, "style",
				"width: 150px; height: 150px; background-color: blue; border: 1px solid black; margin: 5px");
		boxElement = DriverManager.getDriver().findElement(By.id("box0"));
		System.out.println("Background color is : " + boxElement.getCssValue("background-color"));
		System.out.println(" -- Dom Attr Background color is : " + boxElement.getDomAttribute("style"));
		System.out.println(" -- Dom Prop Background color is : " + boxElement.getDomProperty("style"));
		Thread.sleep(1000);
	}

	// @Test
	public void testExplicitWait_WithMultipleWindows() throws Exception {
		String googleWindowTitle = "Google";
		String facebookWindowTitle = "Facebook – log in or sign up";

		getURL("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
		String parentWinId = DriverManager.getDriver().getWindowHandle();
		System.out.println("Parent Windows Id is : " + parentWinId);
		System.out.println("Parent Windows Title is : " + DriverManager.getDriver().getTitle());
		// Get and click the window handling button
		clickElement(By.xpath("//a[@href='http://www.google.com']"));
		clickElement(
				By.xpath("//a[@href='http://www.facebook.com'][normalize-space()='Click this link to start new Tab']"));
		waitForNumberOfWindowsToBe(3);
		try {
			Set<String> allWinId = DriverManager.getDriver().getWindowHandles();
			String child1 = getWindowIdByTitle(DriverManager.getDriver(), allWinId, googleWindowTitle);
			String child2 = getWindowIdByTitle(DriverManager.getDriver(), allWinId, facebookWindowTitle);

			DriverManager.getDriver().switchTo().window(child1);
			String child1Title = DriverManager.getDriver().getTitle();
			System.out.println("Child 1 Title is : " + child1Title);
			Assert.assertEquals(child1Title, googleWindowTitle, "Mismatch Child1 Window Title");
			DriverManager.getDriver().close();

			DriverManager.getDriver().switchTo().window(child2);
			String child2Title = DriverManager.getDriver().getTitle();
			System.out.println("Child 2 Title is : " + child2Title);
			Assert.assertEquals(child2Title, facebookWindowTitle, "Mismatch Child2 Window Title");
			DriverManager.getDriver().close();

			DriverManager.getDriver().switchTo().window(parentWinId);
			String txtParentWin = DriverManager.getDriver().getTitle();
			System.out.println("Parent Window Title is : " + txtParentWin);
			Assert.assertEquals(txtParentWin, "Selenium Practise: Multiple window examples",
					"Mismatch Parent Window Title");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getWindowIdByTitle(WebDriver driver, Set<String> allWinId, String windowTitle) {
		String output = "";
		Iterator<String> itr = allWinId.iterator();
		while (itr.hasNext()) {
			String guid = itr.next();
			driver.switchTo().window(guid);
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
				wait.until(ExpectedConditions.titleContains(windowTitle));
				System.out.println("--- Window with Id  " + guid + " found with title " + windowTitle);
				output = guid;
				break;
			} catch (Exception e) {
				System.out.println("--- Window with Id " + guid + " does NOT contain title " + windowTitle);
			}
		}
		return output;
	}

	private void setDomAttribute(WebElement element, String attrName, String attrVal) {
		JavascriptExecutor j = (JavascriptExecutor) DriverManager.getDriver();
		j.executeScript("arguments[0].setAttribute('" + attrName + "', '" + attrVal + "')", element);
	}
}

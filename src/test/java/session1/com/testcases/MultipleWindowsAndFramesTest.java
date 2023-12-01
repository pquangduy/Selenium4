package session1.com.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MultipleWindowsAndFramesTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		// WebDriverManager.chromedriver().setup();
		// driver = new ChromeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver = new FirefoxDriver();
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));
	}

	@Test
	public void testMultipleWindows1() throws Exception {
		driver.get("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
		String parentWinId = driver.getWindowHandle();
		System.out.println("Parent Windows Id is : " + parentWinId);
		System.out.println("Parent Windows Title is : " + driver.getTitle());
		// Get and click the window handling button
		WebElement btnWinHdl = driver.findElement(By.xpath("//a[@href='http://www.google.com']"));
		explicitWait(driver, btnWinHdl, 10);
		btnWinHdl.click();
		WebElement btnWinHd2 = driver.findElement(
				By.xpath("//a[@href='http://www.facebook.com'][normalize-space()='Click this link to start new Tab']"));
		explicitWait(driver, btnWinHd2, 10);
		btnWinHd2.click();
		waitForNewWindows(driver, 3, 30);
		try {
			Set<String> allWinId = driver.getWindowHandles();
			String child1 = getWindowIdByTitle(driver, allWinId, "Google");
			String child2 = getWindowIdByTitle(driver, allWinId, "Facebook");

			driver.switchTo().window(parentWinId);
			String txtParentWin = driver.getTitle();
			System.out.println("Parent Window Title is : " + txtParentWin);
			Assert.assertEquals(txtParentWin, "Selenium Practise: Multiple window examples",
					"Mismatch Parent Window Title");
			Thread.sleep(1000);

			driver.switchTo().window(child1);
			String child1Title = driver.getTitle();
			System.out.println("Child 1 Title is : " + child1Title);
			Assert.assertEquals(child1Title, "Google", "Mismatch Child1 Window Title");
			Thread.sleep(1000);
			driver.close();

			driver.switchTo().window(child2);
			String child2Title = driver.getTitle();
			System.out.println("Child 2 Title is : " + child2Title);
			Assert.assertEquals(child2Title, "Facebook – log in or sign up", "Mismatch Child2 Window Title");
			Thread.sleep(1000);
			driver.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testMultipleWindows2() throws Exception {
		driver.get("http://seleniumpractise.blogspot.com/2017/07/multiple-window-examples.html");
		String parentWinId = driver.getWindowHandle();
		System.out.println("Parent Windows Id is : " + parentWinId);
		System.out.println("Parent Windows Title is : " + driver.getTitle());
		// Get and click the window handling button
		WebElement btnWinHdl = driver.findElement(By.xpath("//a[@href='http://www.google.com']"));
		explicitWait(driver, btnWinHdl, 10);
		btnWinHdl.click();
		WebElement btnWinHd2 = driver.findElement(
				By.xpath("//a[@href='http://www.facebook.com'][normalize-space()='Click this link to start new Tab']"));
		explicitWait(driver, btnWinHd2, 10);
		btnWinHd2.click();
		waitForNewWindows(driver, 3, 30);
		try {
			String child1 = null;
			String child2 = null;
			Set<String> allWinId = driver.getWindowHandles();
			Iterator<String> itr = allWinId.iterator();
			while (itr.hasNext()) {
				String guid = itr.next();
				if (!parentWinId.equalsIgnoreCase(guid)) {
					if (checkWindowIdByTitle(driver, guid, "Google")) {
						child1 = guid;
						System.out.println("Window Title is : Google");
					} else {
						child2 = guid;
						System.out.println("Window Title is : Facebook");
					}
				}
			}

			driver.switchTo().window(parentWinId);
			String txtParentWin = driver.getTitle();
			System.out.println("Parent Window Title is : " + txtParentWin);
			Assert.assertEquals(txtParentWin, "Selenium Practise: Multiple window examples",
					"Mismatch Parent Window Title");
			Thread.sleep(1000);
			driver.switchTo().window(child1);
			String child1Title = driver.getTitle();
			System.out.println("Child 1 Title is : " + child1Title);
			Assert.assertEquals(child1Title, "Google", "Mismatch Child1 Window Title");
			Thread.sleep(1000);
			driver.close();
			driver.switchTo().window(child2);
			String child2Title = driver.getTitle();
			System.out.println("Child 2 Title is : " + child2Title);
			Assert.assertEquals(child2Title, "Facebook – log in or sign up", "Mismatch Child2 Window Title");
			Thread.sleep(1000);
			driver.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testMultipleWindows3() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		String parentWinId = driver.getWindowHandle();
		System.out.println("Parent Windows Id is : " + parentWinId);
		// Get and click the window handling button
		WebElement btnWinHdl = driver.findElement(By.id("windowhandling1"));
		scrollElementIntoView(btnWinHdl);
		explicitWait(driver, btnWinHdl, 10);
		takeScreenShot(driver, "testMultipleWindows11.jpg");
		btnWinHdl.click();
		try {
			Set<String> allWinId = driver.getWindowHandles();
			System.out.println("All Windows Id are : ");
			for (String id : allWinId) {
				System.out.println(id);
			}

			Iterator<String> itr = allWinId.iterator();
			while (itr.hasNext()) {
				String childWinId = itr.next();
				if (!parentWinId.equalsIgnoreCase(childWinId)) {
					driver.switchTo().window(childWinId);
					By logo = By.xpath("//img[@class='central-featured-logo']");
					waitForElementPresent(driver, logo, 10);
					String txtChildWin = driver.getTitle();
					System.out.println("Child Window Title is : " + txtChildWin);
					Assert.assertEquals(txtChildWin, "Wikipedia", "Mismatch Child Window Title");
					WebElement inputSearch = driver.findElement(By.id("searchInput"));
					explicitWait(driver, inputSearch, 10);
					inputSearch.sendKeys("Selenium Tutorial");
					takeScreenShot(driver, "testMultipleWindows22.jpg");
					driver.close();
				}
			}

			driver.switchTo().window(parentWinId);
			String txtParentWin = driver.getTitle();
			System.out.println("Parent Window Title is : " + txtParentWin);
			Assert.assertEquals(txtParentWin, "Automation Testing Insider: Different Elements on a Web Page",
					"Mismatch Parent Window Title");
			Thread.sleep(2000);
			takeScreenShot(driver, "testMultipleWindows33.jpg");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testMultipleBrowserTabs() throws Exception {
		String strPart2 = "Java Questions Part2 - OOPS Concept, Constructors, Static keyword";

		driver.get("https://www.automationtestinginsider.com/p/java-qa.html");
		String parentWinId = driver.getWindowHandle();
		System.out.println("Parent Windows Id is : " + parentWinId);
		// Scroll down to display the element
		scrollElementIntoView(driver.findElement(By.xpath("//h3[contains(text(),'JAVA Q/A')]")));
		takeScreenShot(driver, "testMultipleBrowserTabs11.jpg");
		// Get and click the links
		WebElement linkPart2 = driver.findElement(By.xpath("//span/a[contains(text(),'" + strPart2 + "')]"));
		explicitWait(driver, linkPart2, 10);
		linkPart2.click();

		try {
			Set<String> allWinId = driver.getWindowHandles();
			System.out.println("All Windows Id are : ");
			for (String id : allWinId) {
				System.out.println(id);
			}

			Iterator<String> itr = allWinId.iterator();
			while (itr.hasNext()) {
				String childWinId = itr.next();
				if (!parentWinId.equalsIgnoreCase(childWinId)) {
					driver.switchTo().window(childWinId);
					String strHeader = strPart2.split("-")[1].trim();
					By tabHeader = By.xpath("//h3[contains(text(),'" + strHeader + "')]");
					waitForElementPresent(driver, tabHeader, 10);
					scrollElementIntoView(driver.findElement(tabHeader));
					String txtChildWin = driver.getTitle();
					System.out.println("Child Window Title is : " + txtChildWin);
					Assert.assertTrue(txtChildWin.contains(strHeader));
					takeScreenShot(driver, childWinId + ".jpg");
					driver.close();
				}
			}

			driver.switchTo().window(parentWinId);
			String txtParentWin = driver.getTitle();
			System.out.println("Parent Window Title is : " + txtParentWin);
			Assert.assertEquals(txtParentWin, "Automation Testing Insider: JAVA Q/A", "Mismatch Parent Window Title");
			takeScreenShot(driver, parentWinId + ".jpg");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testiFrame() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		// First finding the elements using any of locator strategy
		List<WebElement> iframList = driver.findElements(By.tagName("iframe"));
		System.out.println("No of Frames:" + iframList.size());
		// WebElement ele = driver.findElement(By.name("iframe_b"));
		System.out.println("Frame Names are:");
		for (WebElement iframe : iframList) {
			System.out.println("--- Frame Name: " + iframe.getAttribute("name"));
			if (iframe.getAttribute("name").equals("iframe_b")) {
				// switch to frame by element
				driver.switchTo().frame("iframe_b");
				// driver.switchTo().frame(ele);
				// Perform all the required tasks
				By searchInput = By.id("searchInput");
				waitForElementPresent(driver, searchInput, 10);
				driver.findElement(searchInput).sendKeys("iframe Testing");
				// https://github.com/mozilla/geckodriver/issues/2068
				// Security Error thrown when trying to take a Screenshot on Firefox with an
				// iFrame in focus
				// org.openqa.selenium.WebDriverException: SecurityError: Permission denied to
				// access property "pageXOffset" on cross-origin object
				// takeScreenShot(driver, "testiFrame.jpg");
				// Switching back to the main window
				driver.switchTo().defaultContent();
				scrollElementIntoView(driver.findElement(By.xpath("//h2[contains(text(),'Frames')]")));
				takeScreenShot(driver, "testiFrame.jpg");
			}
		}
	}

	private void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	private Boolean waitForNewWindows(WebDriver driver, int NbrOfWindows, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.numberOfWindowsToBe(NbrOfWindows));
		Boolean flag = false;
		int counter = 0;
		while (!flag && counter <= timeOut) {
			try {
				Set<String> winId = driver.getWindowHandles();
				if (winId.size() == NbrOfWindows) {
					flag = true;
				}
				Thread.sleep(1000);
				counter++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return flag;
	}

	private void waitForElementPresent(WebDriver driver, By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	private void scrollElementIntoView(WebElement myElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
	}

	private void takeScreenShot(WebDriver driver, String imgName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\ScreenShots\\" + imgName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Boolean checkWindowIdByTitle(WebDriver driver, String guid, String windowTitle) {
		String strTitle = driver.switchTo().window(guid).getTitle();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.titleContains(windowTitle));
			System.out.println("--- Title: " + strTitle);
			return true;
		} catch (Exception e) {
			return false;
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

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}

package session1.com.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class DynamicContentTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		// WebDriverManager.chromedriver().setup();
		// driver = new ChromeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver = new FirefoxDriver();
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10000));
	}

	// @Test(priority = 1, description = "Input Search With Auto Suggestion")
	public void inputSearchWithAutoSuggestion() throws Exception {
		driver.get("https://www.google.com/");
		WebElement inputSearch = driver.findElement(By.id("APjFqb"));
		explicitWait(driver, inputSearch, 10);
		inputSearch.sendKeys("Java");
		Thread.sleep(1000);
		List<WebElement> list = driver.findElements(By.xpath("//ul[@role='listbox']/li//div[@role='presentation']"));
		System.out.println("Total number of auto suggestion in search box is : " + list.size());
		for (WebElement e : list) {
			System.out.println(e.getText());
			if (e.getText().contains("java download")) {
				e.click();
				break;
			}
		}
		Thread.sleep(1000);
		takeScreenShot(driver, "inputSearchWithAutoSuggestion.jpg");
	}

	// @Test(priority = 2, description = "Select Date From DatePicker/Calendar using
	// sendKeys")
	public void selectDateFromDatePickerWithSendKeys() throws Exception {
		driver.get("https://demo.guru99.com/test/");
		// Find the date time picker control
		WebElement inputDateTime = driver.findElement(By.xpath("//input[@name='bdaytime']"));
		explicitWait(driver, inputDateTime, 10);
		// Fill date as mm/dd/yyyy as 09/25/2013
		inputDateTime.sendKeys("09252013");
		// Press tab to shift focus to time field
		// inputDateTime.sendKeys(Keys.TAB); //TAB not working in Edge browser
		inputDateTime.sendKeys(Keys.ARROW_RIGHT);
		// Fill time as 02:45 PM
		inputDateTime.sendKeys("0245PM");
		WebElement btnSubmit = driver.findElement(By.xpath("//input[@type='submit']"));
		clickElement(driver, btnSubmit, 10);
		WebElement eleDateTime = driver
				.findElement(By.xpath("//div[@class='container-fluid']/following-sibling::div[1]"));
		explicitWait(driver, eleDateTime, 10);
		String actualDateTime = eleDateTime.getText();
		System.out.println("Date Time is : " + actualDateTime);
		takeScreenShot(driver, "selectDateFromDatePickerWithSendKeys.jpg");
	}

	@Test(priority = 3, description = "Select Date From Telerik DateTimePicker control")
	public void selectDateFromTelerikDateTimePicker() throws Exception {
		driver.get("https://demos.telerik.com/kendo-ui/datetimepicker/index");
		// Date and Time to be set in textbox
		String dateTime = "12/07/2014 2:00 PM";
		// Find the button to open calendar
		WebElement btnDate = driver.findElement(By.xpath("//button[@aria-label='Open the date view']/span"));
		clickElement(driver, btnDate, 10);
		// Button to move next in calendar
		WebElement btnNext = driver.findElement(By.xpath("//a[@aria-label='Next']/span"));
		// Button to click in center of calendar header
		WebElement btnMiddle = driver.findElement(By.xpath("//a[@title='Navigate to year view']//span"));
		// Button to move previous month in calendar
		WebElement btnPrevious = driver.findElement(By.xpath("//a[@aria-label='Previous']/span"));
		// Split the date time to get only the date part
		String date_dd_MM_yyyy[] = (dateTime.split(" ")[0]).split("/");
		// get the year difference between current year and year to set in calander
		int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2]) - Calendar.getInstance().get(Calendar.YEAR);

		clickElement(driver, btnMiddle, 10);
		if (yearDiff != 0) {
			// if you have to move next year
			if (yearDiff > 0) {
				for (int i = 0; i < yearDiff; i++) {
					System.out.println("Year Diff->" + i);
					clickElement(driver, btnNext, 10);
				}
			}
			// if you have to move previous year
			else if (yearDiff < 0) {
				for (int i = 0; i < (yearDiff * (-1)); i++) {
					System.out.println("Year Diff->" + i);
					clickElement(driver, btnPrevious, 10);
				}
			}
		}
		// Get all months from calendar to select correct one
		List<WebElement> list_AllMonthToBook = driver.findElements(By.xpath(
				"//div[@id='datetimepicker_dateview']//table//tbody//td[not(contains(@class,'k-other-month'))]"));
		clickElement(driver, list_AllMonthToBook.get(Integer.parseInt(date_dd_MM_yyyy[1]) - 1), 10);
		// get all dates from calendar to select correct one
		List<WebElement> list_AllDateToBook = driver.findElements(By.xpath(
				"//div[@id='datetimepicker_dateview']//table//tbody//td[not(contains(@class,'k-other-month'))]"));
		clickElement(driver, list_AllDateToBook.get(Integer.parseInt(date_dd_MM_yyyy[0]) - 1), 10);
		/// FOR TIME
		WebElement selectTime = driver.findElement(By.xpath("//button[@aria-label='Open the time view']/span"));
		// click time picker button
		clickElement(driver, selectTime, 10);
		// get list of times
		List<WebElement> allTime = driver.findElements(By.xpath("//ul[@id='datetimepicker_timeview']/li/span"));
		dateTime = dateTime.split(" ")[1] + " " + dateTime.split(" ")[2];
		// select correct time
		for (WebElement webElement : allTime) {
			if (webElement.getText().equalsIgnoreCase(dateTime)) {
				System.out.println("Time->" + webElement.getText());
				clickElement(driver, webElement, 10);
			}
		}
		WebElement inputDateTime = driver.findElement(By.xpath("//input[@id='datetimepicker']"));
		String txtdateTime = inputDateTime.getAttribute("value");
		System.out.println("Date Time is : " + txtdateTime);
		Assert.assertEquals(txtdateTime, "7/12/2014 2:00 PM", "Mismatch date time");
		Thread.sleep(1000);
		// takeScreenShot(driver, "selectDateFromTelerikDateTimePicker.png");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	private void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	private void clickElement(WebDriver driver, WebElement element, int timeOut) {
		explicitWait(driver, element, timeOut);
		element.click();
	}

	private void javascriptClick(WebDriver driver, WebElement element, int timeOut) {
		explicitWait(driver, element, timeOut);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
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
}

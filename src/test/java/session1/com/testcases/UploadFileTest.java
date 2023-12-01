package session1.com.testcases;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UploadFileTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver = new FirefoxDriver();
		// WebDriverManager.edgedriver().setup();
		// driver = new EdgeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));
	}

	@Test
	public void testUploadFiles_usingSendKeys() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		WebElement chooseFile = driver.findElement(By.id("fileupload1"));
		scrollElementIntoView(chooseFile);
		explicitWait(driver, chooseFile, 10);
		chooseFile.sendKeys(System.getProperty("user.dir") + "\\OutputFiles\\TestData.xlsx");
		takeScreenShot(driver, "testUploadFiles_usingSendKeys.jpg");
		Thread.sleep(2000);
	}

	// https://tutorialshut.com/upload-a-file-in-selenium-using-sendkeys-autoit-tool-and-robot-class/
	@Test
	public void testUploadFiles_usingRobot() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		WebElement chooseFile = driver.findElement(By.id("fileupload1"));
		scrollElementIntoView(chooseFile);
		explicitWait(driver, chooseFile, 10);

		Actions builder = new Actions(driver);
		builder.moveToElement(chooseFile).click().build().perform();
		// org.openqa.selenium.InvalidArgumentException: invalid argument
		// https://sqa.stackexchange.com/questions/43090/how-to-click-button-which-doesnt-have-button-tag-getting-invalidargumentexcept
		// chooseFile.click();
		Thread.sleep(500);
		uploadFile(System.getProperty("user.dir") + "\\OutputFiles\\TestData.xlsx");
		Thread.sleep(2000);
		takeScreenShot(driver, "testUploadFiles_usingRobot.jpg");
	}

	private void setClipBoard(String file) {
		StringSelection obj = new StringSelection(file);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(obj, null);
	}

	private void uploadFile(String filePath) throws AWTException {
		setClipBoard(filePath);
		Robot rb = new Robot();
		rb.delay(250);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		rb.delay(1000);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		rb.delay(250);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
	}

	private void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(element));
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

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}

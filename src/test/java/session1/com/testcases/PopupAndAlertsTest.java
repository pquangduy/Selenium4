package session1.com.testcases;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class PopupAndAlertsTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver = new FirefoxDriver();
		//WebDriverManager.edgedriver().setup();
		//driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// Selenium 3
		// driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// New in Selenium 4
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2));
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	//@Test
	public void testSimpleAlert() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		// Scroll down to display the simple alert
		// Click the simple alert button
		WebElement btnSimpleAlert = driver.findElement(By.id("simpleAlert"));
		scrollElementIntoView(btnSimpleAlert);
		explicitWait(driver, btnSimpleAlert, 10);
		btnSimpleAlert.click();

		// Get and switch to the simple alert
		try {
			waitForAlertPresent(driver, 10);
			// Can not take screenshot of Alert using getScreenshotAs method of
			// TakesScreenshot
			// It throws exception UnhandledAlertException
			// takeScreenShot(driver, "testSimpleAlert2.png");
			// In Chrome, can not take screenshot using Robot class
			// the page is hanging at Alert displayed
			takeScreenShotFromRobot(driver, "testSimpleAlert2.png");
			Alert simpleAlert = driver.switchTo().alert();
			String txtSimplealert = simpleAlert.getText();
			simpleAlert.accept();
			System.out.println("--- Alert is : " + txtSimplealert);
			Assert.assertTrue(txtSimplealert.contains("Simple Alert"), "Simple Alert not included");
			Assert.assertTrue(txtSimplealert.contains("This is a Simple Alert"), "This is a Simple Alert not included");
		} catch (NoAlertPresentException noAlertEx) {
			noAlertEx.printStackTrace();
		} catch (UnhandledAlertException unhandledAlertEx) {
			unhandledAlertEx.printStackTrace();
		}
	}

	//@Test
	public void testConfirmationAlert() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		// Scroll down to display the confirmation alert
		// Get and click the confirmation alert button
		WebElement btnConfirmAlert = driver.findElement(By.id("confirmationAlert"));
		scrollElementIntoView(btnConfirmAlert);
		explicitWait(driver, btnConfirmAlert, 10);
		btnConfirmAlert.click();
		// Get and switch to the confirmation alert
		try {
			waitForAlertPresent(driver, 10);
			takeScreenShotFromRobot(driver, "testConfirmationAlert.png");
			Alert confirmationAlert = driver.switchTo().alert();
			String txtConfirmAlert = confirmationAlert.getText();
			System.out.println("Alert is : " + txtConfirmAlert);
			confirmationAlert.accept();
			// confirmationAlert.dismiss();

			Assert.assertTrue(txtConfirmAlert.contains("PRESS OK OR Cancel button"), "Confirmation Alert not included");
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
		}
	}

	//@Test
	public void testPromptAlert() throws Exception {
		driver.get("https://www.automationtestinginsider.com/2019/08/textarea-textarea-element-defines-multi.html");
		// Scroll down to display the prompt alert
		// Get and click the prompt alert button
		WebElement btnPromptAlert = driver.findElement(By.id("promptAlert"));
		scrollElementIntoView(btnPromptAlert);
		explicitWait(driver, btnPromptAlert, 10);
		btnPromptAlert.click();
		// Get and switch to the prompt alert
		try {
			waitForAlertPresent(driver, 10);
			Alert promptAlert = driver.switchTo().alert();
			String txtPromptAlert = promptAlert.getText();
			System.out.println("Alert is : " + txtPromptAlert);
			promptAlert.sendKeys("This is prompt alert");
			takeScreenShotFromRobot(driver, "testPromptAlert.png");
			promptAlert.accept();

			Assert.assertTrue(txtPromptAlert.contains("Do you like ATI?"), "Prompt Alert not included");
		} catch (NoAlertPresentException ex) {
			ex.printStackTrace();
		}
	}

	//@Test
	public void testWindowsAuthenticationPopup() throws Exception {
		// Syntax
		// http://username:password@url
		driver.get("https://admin:admin@the-internet.herokuapp.com/basic_auth");
		WebElement header = driver.findElement(By.xpath("//div[@id='content']//h3"));
		explicitWait(driver, header, 10);
		String txtBasicAuth = header.getText();
		System.out.println("Output is : " + txtBasicAuth);
		Assert.assertEquals(txtBasicAuth, "Basic Auth", "Mismatch Header");
		Thread.sleep(2000);
		takeScreenShot(driver, "testWindowsAuthenticationPopup.jpg");
	}

	@Test
	public void testWindowsAuthenticationPopup_usingRobot() throws Exception {
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		inputCredentials("admin", "admin");
		By header = By.xpath("//div[@id='content']//h3");
		waitForElementPresent(driver, header, 10);
		// explicitWait(driver, header, 10);
		String txtBasicAuth = driver.findElement(header).getText();
		System.out.println("Output is : " + txtBasicAuth);
		Assert.assertEquals(txtBasicAuth, "Basic Auth", "Mismatch Header");
		Thread.sleep(2000);
		takeScreenShot(driver, "testWindowsAuthenticationPopup_usingRobot.jpg");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	private void setClipboardData(String text) {
		// StringSelection is a class that can be used for copy and paste operations.
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(text);
		clipboard.setContents(stringSelection, null);
	}

	private void inputCredentials(String username, String password) {
		try {
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();

			setClipboardData(username);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			// takeScreenShotFromRobot(driver, "testWindowsAuthenticationPopup_1.png");
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(500);
			setClipboardData(password);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			// takeScreenShotFromRobot(driver, "testWindowsAuthenticationPopup_2.png");
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(500);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	private void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.visibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	private void waitForAlertPresent(WebDriver driver, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.alertIsPresent());
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
			e.printStackTrace();
		}
	}

	private void takeScreenShotFromRobot(WebDriver driver, String imgName) {
		try {
			// Instantiate the Robot Class
			Robot robotObject = new Robot();
			// Fetch the Details of the Screen Size
			Rectangle screenSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			// Take the Snapshot of the Screen
			BufferedImage image = robotObject.createScreenCapture(screenSize);
			// BufferedImage image = new Robot().createScreenCapture(new
			// Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(System.getProperty("user.dir") + "/ScreenShots/" + imgName));
			// ImageIO.write(image, "png", new File(System.getProperty("user.dir") +
			// "\\ScreenShots\\" + imgName));
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

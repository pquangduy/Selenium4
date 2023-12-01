package session1.com.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class KeyboardAndMouseActionsTest {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		// WebDriverManager.edgedriver().setup();
		// driver = new EdgeDriver();
		// WebDriverManager.firefoxdriver().setup();
		// driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		// Selenium 3
		// driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// New in Selenium 4
		driver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(2));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	}

	//@Test
	public void testEnterUpperCaseLetters() throws Exception {
		driver.get("https://www.google.com/");
		WebElement googleSearch = driver.findElement(By.name("q"));
		Actions act = new Actions(driver);
		act.keyDown(googleSearch, Keys.SHIFT)
			.sendKeys("selenium")
			.keyUp(googleSearch, Keys.SHIFT)
			.build().perform();
//		Action iAct = act.keyDown(googleSearch, Keys.SHIFT)
//				.sendKeys("selenium")
//				.keyUp(googleSearch, Keys.SHIFT)
//				.build();
//		iAct.perform();

		Thread.sleep(2000);
	}

	//@Test
	public void testJQueryToolsTooltip() throws Exception {
		String expectedTooltip = "What's new in 3.2";
		driver.get("https://demo.guru99.com/test/tooltip.html");
		WebElement downloadBtn = driver.findElement(By.id("download_now"));
		Actions act = new Actions(driver);
		//act.moveToElement(downloadBtn).build().perform();
		act.moveToElement(downloadBtn).clickAndHold(downloadBtn).build().perform();
		WebElement toolTipElement = driver.findElement(By.xpath("//div[@class='tooltip']/a"));
		String actualTooltip = toolTipElement.getText();
		System.out.println("Actual Title of Tool Tip  " + actualTooltip);
		Assert.assertEquals(actualTooltip, expectedTooltip, "Mismatch Tooltip");

		Thread.sleep(2000);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}

package session1.com.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserWithoutCookiesTest{

	WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10000));
	}

	@Test(priority = 1, description = "Step 1) Login into application and store the authentication cookie generated.")
	public void testLoginApp() throws Exception {
		driver.get("https://demo.guru99.com/test/cookie/selenium_aut.php");
		// Input Email id and Password If you are already Register		
        driver.findElement(By.name("username")).sendKeys("abc123");							
        driver.findElement(By.name("password")).sendKeys("123xyz");							
        driver.findElement(By.name("submit")).click();
        Thread.sleep(2000);
        WebElement pageTitle = driver.findElement(By.xpath("//div[@id='includedContent']//following-sibling::div//center"));
        String txtPageTitle = pageTitle.getText();
        Assert.assertEquals(txtPageTitle, "You are logged In");
        takeScreenShot(driver, "testLoginApp.png");
	}

	@Test(priority = 2, description = "Step 2) Used the stored cookie, to again login into application without using userid and password.")
	public void testLoginAppWithRefreshBrowser() throws Exception {
		driver.get("https://demo.guru99.com/test/cookie/selenium_aut.php");
		Thread.sleep(2000);
		driver.navigate().refresh();
		Thread.sleep(2000);
		takeScreenShot(driver, "testLoginAppWithRefreshBrowser.png");
	}
	
	private void takeScreenShot(WebDriver driver, String imgName) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
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

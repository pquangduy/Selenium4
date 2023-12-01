package session1.com.testcases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserCookiesTest {

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
		// driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10000));
	}

	@Test(priority = 1, description = "Step 1) Login into application and store the authentication cookie generated.")
	public void testStoringCookieInformation() throws Exception {
		driver.get("https://demo.guru99.com/test/cookie/selenium_aut.php");
		// Input Email id and Password If you are already Register
		driver.findElement(By.name("username")).sendKeys("abc123");
		driver.findElement(By.name("password")).sendKeys("123xyz");
		driver.findElement(By.name("submit")).click();
		Thread.sleep(2000);
		// create file named Cookies to store Login Information
		File file = new File("Cookies.data");
		try {
			// Delete old file if exists
			file.delete();
			file.createNewFile();
			FileWriter fileWrite = new FileWriter(file);
			BufferedWriter Bwrite = new BufferedWriter(fileWrite);
			// loop for getting the cookie information
			for (Cookie ck : driver.manage().getCookies()) {
				System.out.println(" --" + ck.getName() + " -- " + ck.getValue() + " -- " + ck.getDomain() + " -- "
						+ ck.getPath() + " -- " + ck.getExpiry() + " -- " + ck.isSecure());
				Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
						+ ck.getExpiry() + ";" + ck.isSecure()));
				Bwrite.newLine();
			}
			Bwrite.close();
			fileWrite.close();
			WebElement pageTitle = driver
					.findElement(By.xpath("//div[@id='includedContent']//following-sibling::div//center"));
			String txtPageTitle = pageTitle.getText();
			Assert.assertEquals(txtPageTitle, "You are logged In");
			takeScreenShot(driver, "testStoringCookieInformation.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test(priority = 2, description = "Step 2) Used the stored cookie, to again login into application without using userid and password.")
	public void testLoginAppWithStoredCookie() throws Exception {
		driver.get("https://demo.guru99.com/test/cookie/selenium_aut.php");
		printCookieNumber("Delete all cookies ---- ");
		try {
			File file = new File("Cookies.data");
			FileReader fileReader = new FileReader(file);
			BufferedReader Buffreader = new BufferedReader(fileReader);
			String strline;
			while ((strline = Buffreader.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(strline, ";");
				while (token.hasMoreTokens()) {
					String name = token.nextToken();
					String value = token.nextToken();
					String domain = token.nextToken();
					String path = token.nextToken();
					String dateCookie = token.nextToken();
					Date expiry = null;
					System.out.println(" -- " + name);
					System.out.println(" Date " + dateCookie);
					if (dateCookie.toString().equals("null")) {
						System.out.println(" -- Initialize expiry -- ");
						Date today = new Date();
						expiry = new Date(today.getTime() + (1000 * 60 * 60 * 24));
						System.out.println(" -- expiry -- " + expiry.toString());
					}
					Boolean isSecure = Boolean.valueOf(token.nextToken());
					if (name.equalsIgnoreCase("selenium")) {
						// Cookie ck = new Cookie(name,value,domain,path,expiry,true);
						Cookie ck = new Cookie.Builder(name, value).domain(domain).path(path).expiresOn(expiry)
								.isSecure(true).build();
						System.out.println(ck);
						driver.manage().addCookie(ck); // This will add the stored cookie to your current session
						break;
					}
				}
			}
			Buffreader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		driver.navigate().refresh();
		WebElement pageTitle = driver
				.findElement(By.xpath("//div[@id='includedContent']//following-sibling::div//center"));
		String txtPageTitle = pageTitle.getText();
		Assert.assertEquals(txtPageTitle, "You are logged In");
		takeScreenShot(driver, "testLoginAppWithStoredCookie.png");
	}

	private void printCookieNumber(String message) {
		Set<Cookie> cookies = driver.manage().getCookies();
		List<Cookie> cookieList = new ArrayList<>(cookies);
		System.out.println("Total number of cookies ---" + cookieList.size());
		for (int i = 0; i < cookieList.size(); i++) {
			System.out.println("Cookie " + i + ", name: " + cookieList.get(i).getName());
		}
		System.out.println(" -------- ");
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

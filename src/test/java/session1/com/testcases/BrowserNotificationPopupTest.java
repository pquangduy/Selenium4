package session1.com.testcases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserNotificationPopupTest {

	WebDriver driver;

	// How to use a specific chrome profile in selenium?
	// object of ChromeOptions class
	// o = webdriver.ChromeOptions()
	// adding specific Chrome Profile Path
	// o.add_arguments = {'user-data-dir':'<path of specific Chrome profile>'}
	// set chromedriver.exe path
	// driver = webdriver.Chrome(executable_path="C:\chromedriver.exe", options=o)
	// maximize browser
	// driver.maximize_window()
	// launch URL
	// driver.get("https://www.tutorialspoint.com/index.htm")

	// How does Selenium Webdriver handle SSL certificate in Chrome?
	// https://www.tutorialspoint.com/how-does-selenium-webdriver-handle-ssl-certificate-in-chrome
	// ChromeOptions c = new ChromeOptions();
	// set browser properties
	// c.setAcceptInsecureCerts(true);
	// System.setProperty("webdriver.chrome.driver",
	// "C:\Users\ghs6kor\Desktop\Java\chromedriver.exe");
	// WebDriver driver = new ChromeDriver(c);
	// EdgeOptions e = new EdgeOptions();
	// configure setAcceptInsecureCerts to true boolean value
	// e.setAcceptInsecureCerts(true);
	// WebDriver driver = new EdgeDriver(e);
	// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	// driver.get("application url to be entered");

	// How to handle SSL certificate error using Selenium WebDriver?
	// https://www.tutorialspoint.com/how-to-handle-ssl-certificate-error-using-selenium-webdriver
	//// DesiredCapabilities object
	// DesiredCapabilities c=DesiredCapabilities.internetExplorer();
	// set SSL certificate to true
	// c.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	// System.setProperty("webdriver.ie.driver",
	// "C:\Users\ghs6kor\Desktop\Java\IEDriverServer.exe");
	// configure capability to browser
	// WebDriver driver = new InternetExplorerDriver(c);
	// implicit wait
	// driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	// How to handle browser level notification using Selenium Webdriver
	// https://stackoverflow.com/questions/38367762/how-to-handle-browser-level-notification-using-selenium-webdriver
	// @Test
	public void testNotificationPopupOnChrome() throws Exception {
		// For old Chrome version (<50)
		// Create a instance of ChromeOptions class
		// ChromeOptions options = new ChromeOptions();

		// Add chrome switch to disable notification - "**--disable-notifications**"
		// options.addArguments("--disable-notifications");

		// Set path for driver exe
		// System.setProperty("webdriver.chrome.driver","path/to/driver/exe");

		// Pass ChromeOptions instance to ChromeDriver Constructor
		// WebDriver driver =new ChromeDriver(options);

		// For new Chrome version (>50)
		// Create a map to store preferences
		Map<String, Object> prefs = new HashMap<String, Object>();

		// add key and value to map as follow to switch off browser notification
		// Pass the argument 1 to allow and 2 to block, 0 as default
		prefs.put("profile.default_content_setting_values.notifications", 1);

		// Create an instance of ChromeOptions
		ChromeOptions options = new ChromeOptions();
		// set ExperimentalOption - prefs
		options.setExperimentalOption("prefs", prefs);

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));

		driver.get("https://www.redbus.in/");
		takeScreenShot(driver, "testNotificationPopupOnChrome.jpg");
		driver.close();
	}

	// @Test
	public void testNotificationPopupOnChrome_showingBrowserLevelNotification() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));

		driver.get("https://www.redbus.in/");
		takeScreenShot(driver, "testNotificationPopupOnChrome.jpg");
		driver.close();
	}

	// @Test
	public void testNotificationPopupOnFirefox() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("permissions.default.desktop-notification", 1);
		FirefoxOptions ffOpts = new FirefoxOptions();
		ffOpts.setProfile(profile);
		// DesiredCapabilities capabilities=DesiredCapabilities.firefox();
		// capabilities.setCapability(FirefoxDriver.PROFILE, profile);
		// driver = new FirefoxDriver(capabilities);
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver(ffOpts);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));

		driver.get("https://www.redbus.in/");
		takeScreenShot(driver, "testNotificationPopupOnChrome.jpg");
		driver.close();
	}

	@Test
	public void testNotificationPopupOnFirefox_showingBrowserLevelNotification() throws Exception {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20000));

		driver.get("https://www.redbus.in/");
		takeScreenShot(driver, "testNotificationPopupOnFirefox.jpg");
		driver.close();
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

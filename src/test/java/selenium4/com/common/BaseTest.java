package selenium4.com.common;

import static selenium4.com.constants.FrameworkConstants.BROWSER;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.Reporter;
import org.testng.annotations.*;

import selenium4.com.driver.DriverManager;
import selenium4.com.driver.TargetFactory;
import selenium4.com.helpers.PropertiesHelpers;
import selenium4.com.listeners.TestListener;

@Listeners(TestListener.class)
public class BaseTest {
	@BeforeMethod(alwaysRun = true)
	public void createDriver() {
		String browser;
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		if (System.getProperty("BROWSER") != null) {
			browser = System.getProperty("BROWSER").toLowerCase();
		} else {
			browser = BROWSER.toLowerCase();
		}
		//System.out.println("----- Browser in BeforeMethod: " + browser);
		WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));
		DriverManager.setDriver(driver);
		driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		DriverManager.quit();
	}

	public WebDriver createBrowser(@Optional("chrome") String browser) {
		PropertiesHelpers.loadAllFiles();
		WebDriver driver = ThreadGuard.protect(new TargetFactory().createInstance(browser));
		//driver = new TargetFactory1().getTarget(Target.valueOf(FrameworkConstants.TARGET.toUpperCase())).createInstance();
		driver.manage().window().maximize();
		DriverManager.setDriver(driver);
		return DriverManager.getDriver();
	}
}

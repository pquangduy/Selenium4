package selenium4.com.driver;

import org.openqa.selenium.WebDriver;

import selenium4.com.constants.FrameworkConstants;

public class LocalFactory implements ITarget {

	@Override
	public WebDriver createInstance() {
		return BrowserFactory.valueOf(FrameworkConstants.BROWSER.toUpperCase()).createDriver();
	}

	@Override
	public WebDriver createInstance(String browser) {
		return BrowserFactory.valueOf(browser.toUpperCase()).createDriver();
	}
}

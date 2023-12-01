package selenium4.com.driver;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import selenium4.com.constants.FrameworkConstants;

public class RemoteFactory implements ITarget {

	@Override
	public WebDriver createInstance() {
		return createRemoteInstance(BrowserFactory.valueOf(FrameworkConstants.BROWSER.toUpperCase()).getOptions());
	}

	@Override
	public WebDriver createInstance(String browser) {
		return createRemoteInstance(BrowserFactory.valueOf(browser.toUpperCase()).getOptions());
	}

	private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
		RemoteWebDriver remoteWebDriver = null;
		try {
			String gridURL = String.format("http://%s:%s", FrameworkConstants.REMOTE_URL,
					FrameworkConstants.REMOTE_PORT);

			remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
		} catch (java.net.MalformedURLException e) {
			// LogUtils.error("Grid URL is invalid or Grid Port is not available");
			// LogUtils.error(String.format("Browser: %s", capability.getBrowserName()), e);
		} catch (IllegalArgumentException e) {
			// LogUtils.error(String.format("Browser %s is not valid or recognized",
			// capability.getBrowserName()), e);
		}

		return remoteWebDriver;
	}

}

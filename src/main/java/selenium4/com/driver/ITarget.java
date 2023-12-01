package selenium4.com.driver;

import org.openqa.selenium.WebDriver;

public interface ITarget {
	WebDriver createInstance();
	WebDriver createInstance(String browser);
}

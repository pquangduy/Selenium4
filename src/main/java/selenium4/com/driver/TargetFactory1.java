package selenium4.com.driver;

import selenium4.com.enums.Target;
import selenium4.com.exceptions.TargetNotValidException;

public class TargetFactory1 {	
	public ITarget getTarget(Target targetType) {
		switch(targetType) {
		case LOCAL:
			// Create new driver from Enum setup in BrowserFactory class
			return new LocalFactory();
		case REMOTE:
			// Create new driver on Cloud (Selenium Grid, Docker) from method below
			return new RemoteFactory();
		default:
			throw new TargetNotValidException(targetType.toString());
		}
	}
}

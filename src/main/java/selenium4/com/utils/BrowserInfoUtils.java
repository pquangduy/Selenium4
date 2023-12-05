package selenium4.com.utils;

import static selenium4.com.constants.FrameworkConstants.BROWSER;

import org.testng.Reporter;

public final class BrowserInfoUtils {

	private BrowserInfoUtils() {
		super();
	}

	private static final String OS = System.getProperty("os.name").toLowerCase();

    public static String getBrowserInfo() {
    	String xx = System.getProperty("BROWSER").toLowerCase();
    	System.out.println("----xx is : " + xx);
        String browser = "";
        String browserInfo = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER");
        System.out.println("----Browser Info is : " + browserInfo);
        if (Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER") == null) {
            browser = BROWSER.toUpperCase();
            System.out.println("----Browser Info 11 is : " + browser);
        } else {
            browser = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("BROWSER").trim().toUpperCase();
            System.out.println("----Browser Info 22 is : " + browser);
        }
        return browser;
    }

	public static String getOSInfo() {
		return System.getProperty("os.name");
	}

	public static boolean isWindows() {
		return (OS.contains("win"));
	}

	public static boolean isMac() {
		return (OS.contains("mac"));
	}

	public static boolean isUnix() {
		return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
	}

	public static boolean isSolaris() {
		return (OS.contains("sunos"));
	}

}

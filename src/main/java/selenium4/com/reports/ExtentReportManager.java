package selenium4.com.reports;

import static selenium4.com.constants.FrameworkConstants.*;
import selenium4.com.driver.DriverManager;
import selenium4.com.enums.AuthorType;
import selenium4.com.enums.CategoryType;
import selenium4.com.helpers.ScreenshotHelpers;
import selenium4.com.utils.BrowserInfoUtils;
import selenium4.com.utils.DateUtils;
import selenium4.com.utils.IconUtils;
import selenium4.com.utils.ReportUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Objects;

public class ExtentReportManager {

	private static ExtentReports extentReports;
	private static String link = "";

	public static void initReports() {
		if (Objects.isNull(extentReports)) {
			extentReports = new ExtentReports();
			if (OVERRIDE_REPORTS.trim().equals(NO)) {
				System.out.println("OVERRIDE EXTENT REPORTS = " + OVERRIDE_REPORTS);
				link = EXTENT_REPORT_FOLDER_PATH + File.separator + DateUtils.getCurrentDateTimeCustom("_") + "_"
						+ EXTENT_REPORT_FILE_NAME;
				System.out.println("Link Extent Report: " + link);
			} else {
				System.out.println("OVERRIDE EXTENT REPORTS = " + OVERRIDE_REPORTS);
				link = EXTENT_REPORT_FILE_PATH;
				System.out.println("Link Extent Report: " + link);
			}
			ExtentSparkReporter spark = new ExtentSparkReporter(link);
			extentReports.attachReporter(spark);
			spark.config().setTheme(Theme.STANDARD);
			spark.config().setDocumentTitle(REPORT_TITLE);
			spark.config().setReportName(REPORT_TITLE);
			extentReports.setSystemInfo("Framework Name", REPORT_TITLE);
			extentReports.setSystemInfo("Author", AUTHOR);
			System.out.println("Extent Reports is installed.");
		}
	}

	public static void flushReports() {
		if (Objects.nonNull(extentReports)) {
			extentReports.flush();
		}
		ExtentTestManager.unload();
		ReportUtils.openReports(link);
	}

	public static void createTest(String testCaseName) {
		ExtentTestManager.setExtentTest(extentReports.createTest(IconUtils.getBrowserIcon() + " : " + testCaseName));
	}

	public static void createTest(String testCaseName, String description) {
		ExtentTestManager.setExtentTest(extentReports.createTest(testCaseName, description));
	}

	public static void removeTest(String testCaseName) {
		extentReports.removeTest(testCaseName);
	}

	/**
	 * Adds the screenshot.
	 *
	 * @param message the message
	 */
	public static void addScreenShot(String message) {
		String base64Image = "data:image/png;base64,"
				+ ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
		ExtentTestManager.getExtentTest().log(Status.INFO, MediaEntityBuilder
				.createScreenCaptureFromPath(String.valueOf(ScreenshotHelpers.getScreenshotFile(message))).build());
	}

	/**
	 * Adds the screenshot.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public static void addScreenShot(Status status, String message) {
		// String screenshotPath = ScreenshotHelpers.getScreenshotRelativePath(message);
		//String screenshotPath = "http://localhost:8080/job/Maven_LocalMachine/ws/" + ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), message);
		String screenshotPath = "http://localhost:8080/job/Maven_Git/ws/" + ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), message);
		ExtentTestManager.getExtentTest().log(status,
				MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
		System.out.println("---Path: " + screenshotPath);
	}

	synchronized public static void addAuthors(AuthorType[] authors) {
		if (authors == null) {
			ExtentTestManager.getExtentTest().assignAuthor("SELENIUM4");
		} else {
			for (AuthorType author : authors) {
				ExtentTestManager.getExtentTest().assignAuthor(author.toString());
			}
		}
	}

	synchronized public static void addCategories(CategoryType[] categories) {
		if (categories == null) {
			ExtentTestManager.getExtentTest().assignCategory("REGRESSION");
		} else {
			for (CategoryType category : categories) {
				ExtentTestManager.getExtentTest().assignCategory(category.toString());
			}
		}
	}

	synchronized public static void addDevices() {
		ExtentTestManager.getExtentTest().assignDevice(BrowserInfoUtils.getBrowserInfo());
	}

	public static void logMessage(String message) {
		ExtentTestManager.getExtentTest().log(Status.INFO, message);
	}

	public static void logMessage(Status status, String message) {
		ExtentTestManager.getExtentTest().log(status, message);
	}

	public static void logMessage(Status status, Object message) {
		ExtentTestManager.getExtentTest().log(status, (Throwable) message);
	}

	public static void pass(String message) {
		ExtentTestManager.getExtentTest().pass(message);
	}

	public static void pass(Markup message) {
		ExtentTestManager.getExtentTest().pass(message);
	}

	public static void fail(String message) {
		ExtentTestManager.getExtentTest().fail(message);
	}

	public static void fail(Object message) {
		ExtentTestManager.getExtentTest().fail((String) message);
	}

	public static void fail(Markup message) {
		ExtentTestManager.getExtentTest().fail(message);
	}

	public static void skip(String message) {
		ExtentTestManager.getExtentTest().skip(message);
	}

	public static void skip(Markup message) {
		ExtentTestManager.getExtentTest().skip(message);
	}

	public static void info(Markup message) {
		ExtentTestManager.getExtentTest().info(message);
	}

	public static void info(String message) {
		ExtentTestManager.getExtentTest().info(message);
	}

	public static void warning(String message) {
		ExtentTestManager.getExtentTest().log(Status.WARNING, message);
	}
}

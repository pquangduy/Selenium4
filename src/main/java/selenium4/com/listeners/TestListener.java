package selenium4.com.listeners;

import static selenium4.com.constants.FrameworkConstants.*;
import selenium4.com.annotations.FrameworkAnnotation;
import selenium4.com.driver.DriverManager;
import selenium4.com.enums.AuthorType;
import selenium4.com.enums.CategoryType;
import selenium4.com.helpers.ScreenshotHelpers;
import selenium4.com.helpers.PropertiesHelpers;
import selenium4.com.helpers.ScreenRecorderHelpers;
import selenium4.com.helpers.WebElementsHelpers;
import selenium4.com.reports.ExtentReportManager;
import selenium4.com.utils.BrowserInfoUtils;
import selenium4.com.utils.LogUtils;
import com.aventstack.extentreports.Status;
import org.testng.*;

import java.awt.*;
import java.io.IOException;

//public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

	static int count_totalTCs;
	static int count_passedTCs;
	static int count_skippedTCs;
	static int count_failedTCs;

	private ScreenRecorderHelpers screenRecorder;

	public TestListener() {
		try {
			screenRecorder = new ScreenRecorderHelpers();
		} catch (IOException | AWTException e) {
			System.out.println(e.getMessage());
		}
	}

	public String getTestName(ITestResult result) {
		return result.getTestName() != null ? result.getTestName()
				: result.getMethod().getConstructorOrMethod().getName();
	}

	public String getTestDescription(ITestResult result) {
		return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// Before every method in the Test Class
		LogUtils.info(" --- Starting: " + method.getTestMethod().getMethodName());
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// After every method in the Test Class
		LogUtils.info(" --- Ending: " + method.getTestMethod().getMethodName());
	}

	@Override
	public void onStart(ISuite iSuite) {
		System.out.println("========= INSTALLING CONFIGURATION DATA =========");
		PropertiesHelpers.loadAllFiles();
		ExtentReportManager.initReports();
		System.out.println("========= INSTALLED CONFIGURATION DATA =========");
		System.out.println("");
		LogUtils.info("Starting Suite: " + iSuite.getName());
	}

	@Override
	public void onFinish(ISuite iSuite) {
		LogUtils.info("End Suite: " + iSuite.getName());
		WebElementsHelpers.stopSoftAssertAll();
		// End Suite and execute Extents Report
		ExtentReportManager.flushReports();
	}

	public AuthorType[] getAuthorType(ITestResult iTestResult) {
		if (iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class) == null) {
			return null;
		}
		AuthorType authorType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class).author();
		return authorType;
	}

	public CategoryType[] getCategoryType(ITestResult iTestResult) {
		if (iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class) == null) {
			return null;
		}
		CategoryType categoryType[] = iTestResult.getMethod().getConstructorOrMethod().getMethod()
				.getAnnotation(FrameworkAnnotation.class).category();
		return categoryType;
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		LogUtils.info("Test case: " + getTestDescription(iTestResult) + " is starting...");
		count_totalTCs = count_totalTCs + 1;

		ExtentReportManager.createTest(iTestResult.getName());
		ExtentReportManager.addAuthors(getAuthorType(iTestResult));
		ExtentReportManager.addCategories(getCategoryType(iTestResult));
		ExtentReportManager.addDevices();
		ExtentReportManager.info(BrowserInfoUtils.getOSInfo());
		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.startRecording(getTestName(iTestResult));
		}
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		LogUtils.info("Test case: " + getTestDescription(iTestResult) + " is passed.");
		count_passedTCs = count_passedTCs + 1;

		if (SCREENSHOT_PASSED_STEPS.equals(YES)) {
			ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}

		ExtentReportManager.logMessage(Status.PASS, "Test case: " + getTestName(iTestResult) + " is passed.");
		if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
			screenRecorder.stopRecording(true);
		}
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		LogUtils.error("Test case: " + getTestDescription(iTestResult) + " is failed.");
		count_failedTCs = count_failedTCs + 1;
		if (SCREENSHOT_FAILED_STEPS.equals(YES)) {
			ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}
		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.stopRecording(true);
		}
		LogUtils.error("FAILED !! Screenshot for test case: " + getTestName(iTestResult));
		LogUtils.error(iTestResult.getThrowable());
		// Extent report screenshot file and log
		ExtentReportManager.addScreenShot(Status.FAIL, getTestName(iTestResult));
		ExtentReportManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		LogUtils.warn("Test case: " + getTestDescription(iTestResult) + " is skipped.");
		count_skippedTCs = count_skippedTCs + 1;
		if (SCREENSHOT_SKIPPED_STEPS.equals(YES)) {
			ScreenshotHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
		}
		ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");
		if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
			screenRecorder.stopRecording(true);
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		LogUtils.error("Test failed but it is in defined success ratio: " + getTestDescription(iTestResult));
		ExtentReportManager
				.logMessage("Test failed but it is in defined success ratio: " + getTestDescription(iTestResult));
	}
}

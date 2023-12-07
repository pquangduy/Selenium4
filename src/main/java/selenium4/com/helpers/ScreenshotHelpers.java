package selenium4.com.helpers;

import selenium4.com.constants.FrameworkConstants;
import selenium4.com.utils.LogUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotHelpers {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

	public static String captureScreenshot(WebDriver driver, String screenName) {
		try {
			//String path = "http://localhost:8080/job/Jenkin_Git_Parameter1/ws/" + FrameworkConstants.EXPORT_CAPTURE_PATH;
			String aa = FrameworkConstants.PROJECT_PATH;
			System.out.println("-----aa: " + aa);
			String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
			File file = new File(path);
			if (!file.exists()) {
				LogUtils.info("No Folder: " + path);
				file.mkdir();
				LogUtils.info("Folder created: " + file);
			}

			LogUtils.info("Driver for Screenshot: " + driver);
			// Tạo tham chiếu của TakesScreenshot
			TakesScreenshot ts = (TakesScreenshot) driver;
			// Gọi hàm capture screenshot - getScreenshotAs
			File source = ts.getScreenshotAs(OutputType.FILE);
			// result.getName() lấy tên của test case xong gán cho tên File chụp màn hình
			FileUtils.copyFile(source,
					new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
			LogUtils.info("Screenshot taken: " + screenName);
			LogUtils.info("Screenshot taken current URL: " + driver.getCurrentUrl());

			String filePathRelative = FrameworkConstants.EXPORT_CAPTURE_PATH + File.separator + screenName + "_"
					+ dateFormat.format(new Date()) + ".png";
			return filePathRelative;

		} catch (Exception e) {
			LogUtils.error("Exception while taking screenshot: " + e.getMessage());
			return "";
		}
	}

	public static File getScreenshotFile(String screenshotName) {
		Rectangle allScreenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss.SSS").format(new Date());
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(allScreenBounds);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

		String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
		// String path = Helpers.getCurrentDir() +
		// FrameworkConstants.EXTENT_REPORT_FOLDER + File.separator + "images";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
			LogUtils.info("Folder created: " + folder);
		}

		String filePath = path + File.separator + screenshotName + dateName + ".png";
		File file = new File(filePath);
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return file;
	}

	public static String getScreenshotRelativePath(String screenshotName) {
		Rectangle allScreenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss.SSS").format(new Date());
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(allScreenBounds);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

		String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
		// String path = Helpers.getCurrentDir() +
		// FrameworkConstants.EXTENT_REPORT_FOLDER + File.separator + "images";

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
			LogUtils.info("Folder created: " + folder);
		}

		String filePath = path + File.separator + screenshotName + dateName + ".png";

		File file = new File(filePath);
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		String filePathRelative = FrameworkConstants.EXPORT_CAPTURE_PATH + File.separator + screenshotName + dateName
				+ ".png";
//		String filePathRelative = FrameworkConstants.EXTENT_REPORT_FOLDER + File.separator + "images" + File.separator
//				+ screenshotName + dateName + ".png";
		return filePathRelative;
	}

	public static String getScreenshotAbsolutePath(String screenshotName) {
		Rectangle allScreenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss.SSS").format(new Date());
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(allScreenBounds);
		} catch (AWTException e) {
			throw new RuntimeException(e);
		}

		String path = Helpers.getCurrentDir() + FrameworkConstants.EXPORT_CAPTURE_PATH;
		// String path = Helpers.getCurrentDir() +
		// FrameworkConstants.EXTENT_REPORT_FOLDER + File.separator + "images";

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
			LogUtils.info("Folder created: " + folder);
		}

		String filePath = path + File.separator + screenshotName + dateName + ".png";

		File file = new File(filePath);
		try {
			ImageIO.write(image, "PNG", file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return filePath;
	}
}

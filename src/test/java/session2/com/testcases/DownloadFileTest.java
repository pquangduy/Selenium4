package session2.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.io.File;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.constants.FrameworkConstants;
import selenium4.com.driver.DriverManager;

public class DownloadFileTest extends BaseTest {

	//@Test
	public void testWaitForDownloadComplete() throws Exception {
		getURL("https://chromedriver.storage.googleapis.com/index.html?path=114.0.5735.90/");
		clickElementWithJs(By.xpath("//a[contains(text(),'chromedriver_win32.zip')]"));
		Assert.assertTrue(waitForFileDownloaded("chromedriver_win32.zip", 10), "File does NOT exist");
	}

	@Test
    public void  seleniumDownloadFile() throws Exception {
		getURL("https://www.thinkbroadband.com/download");
		clickElement(By.xpath("//button[contains(text(),'Accept')]"));
        
        //We find the download links
        List<WebElement> list =DriverManager.getDriver().findElements(By.cssSelector("div.module>p>a>img"));

        //Click to 5MB web element
        WebElement el = list.get(list.size()-1);
        el.click();
//        Thread.sleep(500);
//        //Hide Google Popup Ad
//        JavascriptExecutor js = (JavascriptExecutor)DriverManager.getDriver();
//        js.executeScript("document.querySelector(\"html > ins\").style.display='none'");
//        //Again click to 5MB web element
//        el.click();

        //Wait 15 seconds to download 5MB file.
        //You can write custom wait. Check Selenium Wait article on swtestacademy.com
        Thread.sleep(15000);

        //Get the user.dir folder
        File folder = new File(FrameworkConstants.DOWNLOAD_FOLDER_PATH);

        //List the files on that folder
        File[] listOfFiles = folder.listFiles();

        boolean found = false;
        //Look for the file in the files
        // You should write smart REGEX according to the filename
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                String fileName = listOfFile.getName();
                System.out.println(" --- File " + listOfFile.getName());
                if (fileName.matches("5MB.zip" )) {
                    found = true;
                    break;
                }
            }
        }

        Assert.assertTrue(found, "Downloaded document is not found");
    }
	
	private Boolean waitForFileDownloaded(String fileName) {
		FluentWait<WebDriver> wait = new FluentWait<>(DriverManager.getDriver())
				.withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return wait.until((x) -> {
			File[] files = new File(FrameworkConstants.DOWNLOAD_FOLDER_PATH).listFiles();
	        boolean found = false;
	        //Look for the file in the files
	        // You should write smart REGEX according to the filename
	        for (File f : files) {
	            if (f.isFile()) {
	                System.out.println(" --- File " + f.getName());
	                if (f.getName().matches(fileName)) {
	                    found = true;
	                    break;
	                }
	            }
	        }
	        return found;
		});
	}

	private Boolean waitForFileDownloaded(String fileName, int timeoutSeconds) {
		FluentWait<WebDriver> wait = new FluentWait<>(DriverManager.getDriver())
				.withTimeout(Duration.ofSeconds(timeoutSeconds)).pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		return wait.until((x) -> {
			File[] files = new File(FrameworkConstants.DOWNLOAD_FOLDER_PATH).listFiles();
			for (File file : files) {
				System.out.println("-- File name : " + file.getName());
				if (file.getName().equals(fileName)) {
					System.out.println("-- True here ");
					return true;
				}
			}
			return false;
		});
	}
}

package session3.com.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.constants.FrameworkConstants;
import selenium4.com.helpers.ExcelHelpers;
import selenium4.com.helpers.Helpers;
import selenium4.com.helpers.PropertiesHelpers;
import selenium4.com.helpers.WebElementsHelpers;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//public class ExcelFileTest extends BaseTest {
public class ExcelFileTest extends BaseTest{

    //Run with DataProvider
    //@Test()
    public void testExcelFile_getExcelData() throws Exception {
        PropertiesHelpers.loadAllFiles();
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] res = excelHelpers.getExcelData(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "Client_1");
        for(int i=0; i<res.length; i++)
        {
        	for(int j=0; j<res[i].length; j++)
        		WebElementsHelpers.logConsole(res[i][j]);
        	WebElementsHelpers.logConsole("==========");
        }
    }

    //Run with DataProvider
    //@Test()
    public void testExcelFile_getDataHashTable() throws Exception {
        PropertiesHelpers.loadAllFiles();
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] res = excelHelpers.getDataHashTable(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "Client_1", 1, 2);
        for(int i=0; i<res.length; i++)
        	for(int j=0; j<res[i].length; j++)
        		WebElementsHelpers.logConsole(res[i][j]);
    }

    //@Test()
    public void testExcelFile_writeDateFormat() {
    	PropertiesHelpers.loadAllFiles();
    	ExcelHelpers excelHelpers = new ExcelHelpers();
    	WebElementsHelpers.logConsole("Excel file path is: " + excelHelpers.getExcelFilePath());
		try {
			Date d = new SimpleDateFormat("MM-dd-yyyy").parse("10-20-2018");
	    	//excelHelpers.setCellData(d, "dd-mm-yyyy", 1, 0);
	    	//excelHelpers.setCellData(d, "dd/mm/yyyy", 2, 0);
	    	//excelHelpers.setCellData(d, "mm/dd/yyyy", 3, 0);
	    	excelHelpers.setCellData(d, "mm-dd-yyyy hh:mm:ss", 4, 0);
	    	//excelHelpers.setCellData(d, "hh:mm:ss", 5, 0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

    //@Test
    public void testExcelFile_writeStringFormat() {
        PropertiesHelpers.loadAllFiles();
        ExcelHelpers excelHelpers = new ExcelHelpers("Client");
        WebElementsHelpers.logConsole(Helpers.getCurrentDir() + PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH"));
        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "COMPANY_NAME"));
        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "ZIP"));
        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "WEBSITE"));
        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "DATE_START"));
        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "STATUS"));
        excelHelpers.setCellData("failed", 1, "STATUS");
        //excelHelpers.setCellData("passed", 1, "STATUS");
    }

    @Test()
    public void testExcelFile_writeWebTable() {
    	By webTable = By.xpath("//table[@class='wikitable sortable jquery-tablesorter']");
    	String sheetName = "WebScraping";
    	String excelFile = "src/test/resources/testData/ClientsDataExcel2.xls";

    	getURL("https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_population");
    	ExcelHelpers excelHelpers = new ExcelHelpers();
    	excelHelpers.setExcelFile(Helpers.getCurrentDir() + excelFile, sheetName);
    	scrollToElementToTop(webTable);
		WebElement table = waitForElementPresent(webTable);
		//Write headers in excel sheet
		List<WebElement> headers = table.findElements(By.xpath("thead/tr/th"));
		WebElementsHelpers.logConsole("Headers : " + headers.size());
		for(int i=0; i<headers.size(); i++) {
			if (!headers.get(i).getText().isEmpty()) {
				excelHelpers.setCellData(headers.get(i).getText(), 0, i);				
			}
		}
		//capture table rows
		WebElement body = table.findElement(By.xpath("tbody"));
		int rows = body.findElements(By.xpath("tr")).size(); // rows present in web table
		for(int r=1;r<=rows;r++)
		{
			//read data from web table
			String country=body.findElement(By.xpath("tr["+r+"]/td[1]")).getText();
			String population=body.findElement(By.xpath("tr["+r+"]/td[2]")).getText();
			String perOfWorld=body.findElement(By.xpath("tr["+r+"]/td[3]")).getText();
			String date=body.findElement(By.xpath("tr["+r+"]/td[4]")).getText();
			String source=body.findElement(By.xpath("tr["+r+"]/td[5]")).getText();
			System.out.println(country+population+perOfWorld+date+source);
			//writing the data in excel sheet
			excelHelpers.setCellData(country, r, 1);
			excelHelpers.setCellData(population, r, 2);
			excelHelpers.setCellData(perOfWorld, r, 3);
			excelHelpers.setCellData(date, r, 4);
			excelHelpers.setCellData(source, r, 5);
		}
		System.out.println("Web scrapping is done succesfully.....");
    }
}

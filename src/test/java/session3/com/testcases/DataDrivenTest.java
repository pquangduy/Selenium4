package session3.com.testcases;

import session3.com.dataprovider.DataProviderFactory;

import java.util.HashMap;
import java.util.Hashtable;

import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.helpers.WebElementsHelpers;

public class DataDrivenTest{

    //@Test(dataProvider="dpGetData_FromExcel", dataProviderClass = DataProviderFactory.class)
    public void testExcelFile_getExcelData(String companyName, String testcaseName, String application) throws Exception {
    	WebElementsHelpers.logConsole(companyName);
    	WebElementsHelpers.logConsole(testcaseName);
    	WebElementsHelpers.logConsole(application);
    	WebElementsHelpers.logConsole("------");
    }

    //@Test(dataProvider="dpGetHashTableData_FromExcel", dataProviderClass = DataProviderFactory.class)
    public void testExcelFile_getDataHashTable(Hashtable<String, String> data) throws Exception {
    	WebElementsHelpers.logConsole(data.get("COMPANY_NAME"));
    	WebElementsHelpers.logConsole(data.get("TESTCASENAME"));
    	WebElementsHelpers.logConsole(data.get("APPLICATION"));
    	WebElementsHelpers.logConsole("------");
    }
    
    //@Test(dataProvider="dpGetData_FromDatabase", dataProviderClass = DataProviderFactory.class)
    public void testDBMySQL_getData(String name, Integer salary) throws Exception {
    	WebElementsHelpers.logConsole("---In the test: testDBMySQL_getData");
    	WebElementsHelpers.logConsole(name);
    	WebElementsHelpers.logConsole(salary);
    }

    @Test(dataProvider="dpGetHashMapData_FromDatabase", dataProviderClass = DataProviderFactory.class)
    public void testDBMySQL_getHashMapData(HashMap<String, String> data) throws Exception {
    	WebElementsHelpers.logConsole("---In the test: testDBMySQL_getHashMapData");
    	WebElementsHelpers.logConsole(data.getOrDefault("name", "undefined name"));
    	WebElementsHelpers.logConsole(data.getOrDefault("salary", "0"));
    }
}

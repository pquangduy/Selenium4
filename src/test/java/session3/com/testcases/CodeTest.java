package session3.com.testcases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.testng.annotations.Test;

import selenium4.com.constants.FrameworkConstants;
import selenium4.com.helpers.DatabaseHelpers;
import selenium4.com.helpers.ExcelHelpers;
import selenium4.com.helpers.Helpers;
import selenium4.com.helpers.JsonHelpers;
import selenium4.com.helpers.PropertiesHelpers;
import selenium4.com.helpers.WebElementsHelpers;

public class CodeTest {

//    //@Test
//    public void testExcelFile_getCellData() {
//        PropertiesHelpers.loadAllFiles();
//        ExcelHelpers excelHelpers = new ExcelHelpers();
//        excelHelpers.setExcelFile(Helpers.getCurrentDir() + PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH"), "Client");
//        WebElementsHelpers.logConsole(Helpers.getCurrentDir() + PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH"));
//        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "COMPANY_NAME"));
//        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "ZIP"));
//        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "DATE_START"));
//        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "DATE_CREATION"));
//        WebElementsHelpers.logConsole(excelHelpers.getCellData(1, "STATUS"));
//        excelHelpers.setCellData("pass", 1, "STATUS");
//    }
//
//    //@Test()
//    public void testExcelFile_getExcelData() throws Exception {
//        PropertiesHelpers.loadAllFiles();
//        ExcelHelpers excelHelpers = new ExcelHelpers();
//        Object[][] res = excelHelpers.getExcelData(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "SignIn");
//        WebElementsHelpers.logConsole(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH);
//        for(int i=0; i<res.length; i++)
//        	for(int j=0; j<res[i].length; j++)
//        		WebElementsHelpers.logConsole(res[i][j]);
//    }
//
//    //@Test()
//    public void testExcelFile_getDataHashTable() throws Exception {
//        PropertiesHelpers.loadAllFiles();
//        ExcelHelpers excelHelpers = new ExcelHelpers();
//        Object[][] res = excelHelpers.getDataHashTable(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "SignIn", 1, 2);
//        for(int i=0; i<res.length; i++)
//        	for(int j=0; j<res[i].length; j++)
//        		WebElementsHelpers.logConsole(res[i][j]);
//    }

  @Test
  public void connectDBMySQL_usingMap() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String sqlSelect = "select * from employee;";
	  Object hostname = jsonHelpers.getData("$['database']['hostname']");
	  Object dbName = jsonHelpers.getData("$['database']['dbName']");
	  Object dbUserName = jsonHelpers.getData("$['database']['dbUserName']");
	  Object dbPassword = jsonHelpers.getData("$['database']['dbPassword']");
	  dbHelpers.setDbConnection((String)hostname, (String)dbName, (String)dbUserName, (String)dbPassword);
	  Object[][] data = dbHelpers.getSqlResultInHashMap(sqlSelect);
	  System.out.println("---------------------------");
      for(int i=0; i<data.length; i++) {
    	  System.out.println("---length x: " + data.length);
    	  for(int j=0; j<data[i].length; j++) {
    		  System.out.println("---length y: " + data[i].length);
    		  WebElementsHelpers.logConsole(data[i][j]);
    	  }    		  
      }
//      HashMap<String, String> data_map = dbHelpers.getSqlResultInMap(sqlSelect);
//	  for(String k : data_map.keySet()) {
//		  System.out.println("--- Value: " + data_map.get(k));
//	  }
  }

  //@Test
  public void connectDBMySQL_usingMultiDimension() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String sqlSelect = "select * from employee;";
	  Object hostname = jsonHelpers.getData("$['database']['hostname']");
	  Object dbName = jsonHelpers.getData("$['database']['dbName']");
	  Object dbUserName = jsonHelpers.getData("$['database']['dbUserName']");
	  Object dbPassword = jsonHelpers.getData("$['database']['dbPassword']");
	  dbHelpers.setDbConnection((String)hostname, (String)dbName, (String)dbUserName, (String)dbPassword);
	  Object[][] data = dbHelpers.getSqlResult(sqlSelect);
      for(int i=0; i<data.length; i++)
      	for(int j=0; j<data[i].length; j++)
      		WebElementsHelpers.logConsole(data[i][j]);
  }

  //@Test
  public void executeSqlUpdate() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String sqlUpdate = "update employee set salary=2600 where name='JOHN'";
	  Object hostname = jsonHelpers.getData("$['database']['hostname']");
	  Object dbName = jsonHelpers.getData("$['database']['dbName']");
	  Object dbUserName = jsonHelpers.getData("$['database']['dbUserName']");
	  Object dbPassword = jsonHelpers.getData("$['database']['dbPassword']");
	  dbHelpers.setDbConnection((String)hostname, (String)dbName, (String)dbUserName, (String)dbPassword);
	  dbHelpers.executeSqlUpdate(sqlUpdate);
  }

  //@Test
  public void executeSqlInsert() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String sqlInsert = "insert into employee values ('Messi', 18000);";
	  Object hostname = jsonHelpers.getData("$['database']['hostname']");
	  Object dbName = jsonHelpers.getData("$['database']['dbName']");
	  Object dbUserName = jsonHelpers.getData("$['database']['dbUserName']");
	  Object dbPassword = jsonHelpers.getData("$['database']['dbPassword']");
	  dbHelpers.setDbConnection((String)hostname, (String)dbName, (String)dbUserName, (String)dbPassword);
	  dbHelpers.executeSqlUpdate(sqlInsert);
  }

  //@Test
  public void executeSqlDelete() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String sqlDelete = "delete from employee where name='Lionel';";
	  Object hostname = jsonHelpers.getData("$['database']['hostname']");
	  Object dbName = jsonHelpers.getData("$['database']['dbName']");
	  Object dbUserName = jsonHelpers.getData("$['database']['dbUserName']");
	  Object dbPassword = jsonHelpers.getData("$['database']['dbPassword']");
	  dbHelpers.setDbConnection((String)hostname, (String)dbName, (String)dbUserName, (String)dbPassword);
	  dbHelpers.executeSqlUpdate(sqlDelete);
  }

}

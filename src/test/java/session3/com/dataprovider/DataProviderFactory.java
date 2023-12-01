package session3.com.dataprovider;

import java.sql.SQLException;
import org.testng.annotations.DataProvider;

import selenium4.com.constants.FrameworkConstants;
import selenium4.com.helpers.DatabaseHelpers;
import selenium4.com.helpers.ExcelHelpers;
import selenium4.com.helpers.Helpers;
import selenium4.com.helpers.JsonHelpers;
import selenium4.com.helpers.PropertiesHelpers;

public class DataProviderFactory {

    private DataProviderFactory() {
        super();
        PropertiesHelpers.loadAllFiles();
    }

    @DataProvider(name = "dpGetData_FromExcel")
    public static Object[][] getExcelData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        
        return excelHelpers.getExcelData(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "Client_1");
    }

    @DataProvider(name = "dpGetHashTableData_FromExcel")
    public static Object[][] getHashTableData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();

        return excelHelpers.getDataHashTable(Helpers.getCurrentDir() + FrameworkConstants.EXCEL_DATA_FILE_PATH, "Client_1", 1, 2);
    }

    @DataProvider(name = "dpGetHashMapData_FromDatabase")
    public static Object[][] connectDBMySQL_usingHashMap() throws SQLException, ClassNotFoundException {
  	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
  	  JsonHelpers jsonHelpers = new JsonHelpers();
  	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
  	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
  	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
  	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
  	  String sqlSelect = (String) jsonHelpers.getData("$['sql_select']");	
  	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);

  	  return dbHelpers.getSqlResultInHashMap(sqlSelect);
    }

    @DataProvider(name = "dpGetData_FromDatabase")
    public static Object[][] connectDBMySQL_usingMultiDimension() throws SQLException, ClassNotFoundException {
  	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
  	  JsonHelpers jsonHelpers = new JsonHelpers();
  	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
  	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
  	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
  	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
  	  String sqlSelect = (String) jsonHelpers.getData("$['sql_select']");	
  	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);

	  return dbHelpers.getSqlResult(sqlSelect);
    }
}

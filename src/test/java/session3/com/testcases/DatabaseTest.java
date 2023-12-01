package session3.com.testcases;

import java.sql.SQLException;
import org.testng.annotations.Test;

import selenium4.com.helpers.DatabaseHelpers;
import selenium4.com.helpers.Helpers;
import selenium4.com.helpers.JsonHelpers;
import selenium4.com.helpers.PropertiesHelpers;
import selenium4.com.helpers.WebElementsHelpers;

public class DatabaseTest {

  //@Test
  public void connectDBMySQL_usingMap() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();

	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
	  String sqlSelect = (String) jsonHelpers.getData("$['sql_select']");	
	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);
	  Object[][] data = dbHelpers.getSqlResultInHashMap(sqlSelect);
	  System.out.println("---------------------------");
      for(int i=0; i<data.length; i++) {
    	  for(int j=0; j<data[i].length; j++) {
    		  WebElementsHelpers.logConsole(data[i][j]);
    	  }    		  
      }
  }

  @Test
  public void connectDBMySQL_usingMultiDimension() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();
	  
	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
	  String sqlSelect = (String) jsonHelpers.getData("$['sql_select']");	
	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);
	  Object[][] data = dbHelpers.getSqlResult(sqlSelect);
	  System.out.println("---------------------------");
      for(int i=0; i<data.length; i++)
      	for(int j=0; j<data[i].length; j++)
      		WebElementsHelpers.logConsole(data[i][j]);
  }

  //@Test
  public void executeSqlUpdate() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();

	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
	  String sqlUpdate = (String) jsonHelpers.getData("$['sql_update']");	
	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);
	  dbHelpers.executeSqlUpdate(sqlUpdate);
  }

  //@Test
  public void executeSqlInsert() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();

	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
	  String sqlInsert = (String) jsonHelpers.getData("$['sql_insert']");	
	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);
	  dbHelpers.executeSqlUpdate(sqlInsert);
  }

  //@Test
  public void executeSqlDelete() throws SQLException, ClassNotFoundException {
	  PropertiesHelpers.loadAllFiles();

	  DatabaseHelpers dbHelpers = new DatabaseHelpers();
	  JsonHelpers jsonHelpers = new JsonHelpers();
	  String hostname = (String) jsonHelpers.getData("$['mysql_database']['hostname']");
	  String dbName = (String) jsonHelpers.getData("$['mysql_database']['dbName']");
	  String dbUserName = (String) jsonHelpers.getData("$['mysql_database']['dbUserName']");
	  String dbPassword = (String) jsonHelpers.getData("$['mysql_database']['dbPassword']");
  	  jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/sql_queries.json");
	  String sqlDelete = (String) jsonHelpers.getData("$['sql_delete']");	
	  dbHelpers.setDbConnection(hostname, dbName, dbUserName, dbPassword);
	  dbHelpers.executeSqlUpdate(sqlDelete);
  }
}

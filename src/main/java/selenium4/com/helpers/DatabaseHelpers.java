package selenium4.com.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import selenium4.com.exceptions.FrameworkException;

public class DatabaseHelpers {

	private Connection conn = null;
	private String dbUserName;
	private String dbPassword;
	private String dbConnectionUrl;

	public DatabaseHelpers() {
		super();
	}

	public void setDbConnection(String hostName, String dbName, String userName, String password) {
		dbConnectionUrl = "jdbc:mysql://" + hostName + ":3306/" + dbName;
		//dbConnectionUrl = "jdbc:oracle:thin:@localhost:1521:xe";
		dbUserName = userName;
		dbPassword = password;
	}
	
	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(dbConnectionUrl, dbUserName, dbPassword);
			} catch (SQLException e) {
				throw new FrameworkException(e.getMessage());
			}
		}
		return conn;
	}

	public Connection getConnection(String hostName, String dbName, String userName, String password) throws SQLException {
		if (conn == null) {
			try {
				String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
				conn = DriverManager.getConnection(connectionURL, userName, password);
			} catch (SQLException e) {
				throw new FrameworkException(e.getMessage());
			}
		}
		return conn;
	}

	public synchronized Object[][] getSqlResultInHashMap(String sql) {
		Object[][] data = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //MySQL Connector
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);			
			ResultSetMetaData md = rs.getMetaData();
			
			rs.last();			
			int noOfRows = rs.getRow();
			int noOfCols = md.getColumnCount();
			rs.beforeFirst();

			System.out.println("Number of rows: " + noOfRows);
			System.out.println("Number of cols: " + noOfCols);
			data = new Object[noOfRows][1];
			HashMap<String, String> data_map = null;
			int row = 0;
			while (rs.next()) {
				data_map = new HashMap<>();
				for (int i = 1; i <= noOfCols; i++) {
					data_map.put(md.getColumnName(i), rs.getString(i));
				}
				data[row][0] = data_map;
				System.out.println("data: " + data_map.toString());
				row++;
			}
			closeStatement(stmt);
			closeConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
		return data;
	}

	public synchronized Object[][] getSqlResult(String sql) {
		Object[][] data = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);			
			rs.last();			
			int noOfRows = rs.getRow();
			int noOfCols = rs.getMetaData().getColumnCount();
			rs.beforeFirst();

			System.out.println("Number of rows: " + noOfRows);
			System.out.println("Number of cols: " + noOfCols);
			data = new Object[noOfRows][noOfCols];
			int iRow = 0;
			while (rs.next()) {
				for (int iCol = 0; iCol < noOfCols; iCol++) {
					Object obj = rs.getObject(iCol + 1); // the first Column is 1
					data[iRow][iCol] = (obj == null)?null:obj;
				}
				iRow++;
			}
			closeStatement(stmt);
			closeConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
		return data;
	}

	public synchronized void executeSqlUpdate(String sql) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = getConnection();
			Statement stmt = con.createStatement();
			int rs = stmt.executeUpdate(sql);
			System.out.println("Number of records updated is: " + rs);
			closeStatement(stmt);
			closeConnection();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new FrameworkException(e.getMessage());
			}
		}
	}

	public void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new FrameworkException(e.getMessage());
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new FrameworkException(e.getMessage());
			}
		}
	}
}

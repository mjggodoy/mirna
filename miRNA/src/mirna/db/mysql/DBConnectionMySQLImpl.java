package mirna.db.mysql;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import mirna.db.DBConnection;
import mirna.exception.MiRnaException;

public class DBConnectionMySQLImpl implements DBConnection {
	
    Connection conn;	//our connnection to the db - presist for life of program

    // we dont want this garbage collected until we are done
    public DBConnectionMySQLImpl() throws MiRnaException {    // note more general exception
    	System.out.println("MiRNA: getDBConnection began-----------.");
		String driverClassName = "";
		String db_file_name_prefix = "";

		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna.properties"));	
			driverClassName = props.getProperty("driverName");
			System.out.println("driverClassName=" + driverClassName);
			db_file_name_prefix = props.getProperty("dbFileNamePrefix");
		} catch (FileNotFoundException e) {
			throw new MiRnaException("FileNotFoundException:" + e.getMessage() + " " + e.toString());
		} catch (java.io.IOException e) {
			throw new MiRnaException("java.io.IOException:" + e.getMessage() + " " + e.toString());
		}

		try {
			Class.forName(driverClassName); //org.hsqldb.jdbcDriver
			
			conn = DriverManager.getConnection("jdbc:mysql:"
				+ db_file_name_prefix, // filenames
				"sa", // username
				""); // password
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage() + " " + ex.toString());
		} catch (java.lang.ClassNotFoundException ex) {
			throw new MiRnaException("ClassNotFoundException:" + ex.getMessage() + " " + ex.toString());
		}
    }
	
	public void closeDBConnection() throws MiRnaException {
		//System.out.println("MiRNA: closeDBConnection began-----------.");
		try {
			conn.close();
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		}
	}
	
	public void closeStatement(PreparedStatement stmt) throws MiRnaException {
		//System.out.println("MiRNA: closeStatement began-----------.");
		try {
			stmt.close();
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage());
		}
	}

	public synchronized void update(String expression) throws SQLException {
		//for CREATE, DROP, INSERT and UPDATE
		System.out.println("MiRNA: update began-----------.");
		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}
		st.close();
	}

	public synchronized List<Map<String, Object>> query(String expression) throws SQLException {
		//for SELECT
		System.out.println("MiRNA: query began-----------.");
		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement();
		rs = st.executeQuery(expression);
		
		List<Map<String, Object>> list = resultSetToArrayList(rs);

		dump(rs);
		st.close();
		
		return list;
	}

	private static void dump(ResultSet rs) throws SQLException {
		System.out.println("MiRNA: dump began-----------.");
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;

		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1);
				System.out.print(o.toString() + " ");
			}
			System.out.println(" ");
		}
		System.out.println();
	}
	
	private List<Map<String, Object>> resultSetToArrayList(ResultSet rs) throws SQLException{
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Object> row = new HashMap<String, Object>(columns);
			for(int i=1; i<=columns; ++i) {           
				row.put(md.getColumnName(i),rs.getObject(i));
			}
			list.add(row);
		}
		return list;
	}

	public void shutdown() throws SQLException {
		System.out.println("MiRNA: shutdown began-----------.");
		Statement st = conn.createStatement();

		st.execute("SHUTDOWN"); //write out buffersand clean shut down
		conn.close(); // if there are no other open connection
	}

}

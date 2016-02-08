package mirna.integration.db.mysql;

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

import mirna.integration.db.DBConnection;
import mirna.integration.exception.MiRnaException;

public class DBConnectionMySQLImpl implements DBConnection {
	
    Connection conn;	//our connnection to the db - presist for life of program

    // we dont want this garbage collected until we are done
    public DBConnectionMySQLImpl() throws MiRnaException {    // note more general exception
    	//System.out.println("MIRNA: getDBConnection began-----------.");

    	String url = "";
    	String user = "";
		String password = "";

		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
			url = props.getProperty("url");
			user = props.getProperty("user");
			password = props.getProperty("password");
		} catch (FileNotFoundException e) {
			throw new MiRnaException("FileNotFoundException:" + e.getMessage() + " " + e.toString());
		} catch (java.io.IOException e) {
			throw new MiRnaException("java.io.IOException:" + e.getMessage() + " " + e.toString());
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			throw new MiRnaException("SQLException:" + ex.getMessage() + " " + ex.toString());
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
	
	public synchronized int insert(String expression) throws SQLException {
		// for INSERT and returning new auto-increment key
		//System.out.println("MIRNA: insert began-----------.");

		PreparedStatement pstmt = conn.prepareStatement(expression, Statement.RETURN_GENERATED_KEYS);  
		pstmt.executeUpdate();  
		
		ResultSet keys = pstmt.getGeneratedKeys();    
		keys.next();  
		int key = keys.getInt(1);
		
		pstmt.close();
		return key;
	}

	public synchronized void update(String expression) throws SQLException {
		//for CREATE, DROP, INSERT and UPDATE
		//System.out.println("MIRNA: update began-----------.");
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
		//System.out.println("MIRNA: query began-----------.");
		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement();
		rs = st.executeQuery(expression);
		
		List<Map<String, Object>> list = resultSetToArrayList(rs);

		//dump(rs);
		st.close();
		
		return list;
	}

	/*
	private static void dump(ResultSet rs) throws SQLException {
		//System.out.println("MIRNA: dump began-----------.");
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
	*/
	
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
		//System.out.println("MIRNA: shutdown began-----------.");
		Statement st = conn.createStatement();

		st.execute("SHUTDOWN"); //write out buffersand clean shut down
		conn.close(); // if there are no other open connection
	}

}

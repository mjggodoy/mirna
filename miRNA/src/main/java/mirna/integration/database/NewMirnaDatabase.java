package mirna.integration.database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.Transaction;

import mirna.integration.exception.MiRnaException;
import mirna.integration.utils.HibernateUtil;

public abstract class NewMirnaDatabase implements IMirnaDatabase {
	
	protected String dbUrl;
	protected String dbUser;
	protected String dbPassword;
	
	protected String selectQuery = "select * from %s";
	
	// Number of rows to read from the database to be inserted into the model.
	// Use -1 to read all the rows.
	protected final int ROWS_TO_READ = Integer.MAX_VALUE;
	
	protected String tableName;
	
	protected boolean fetchSizeMin = false;
	
	protected NewMirnaDatabase(String tableName) throws MiRnaException {
		try {
			Properties props = new Properties();
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
			this.dbUrl = props.getProperty("url");
			this.dbUser = props.getProperty("user");
			this.dbPassword = props.getProperty("password");
			this.tableName = tableName;
		} catch (FileNotFoundException e) {
			throw new MiRnaException("FileNotFoundException:" + e.getMessage() + " " + e.toString());
		} catch (java.io.IOException e) {
			throw new MiRnaException("java.io.IOException:" + e.getMessage() + " " + e.toString());
		}
	}
	
	protected abstract void processRow(Session session, ResultSet rs) throws Exception; 
	
	public void insertIntoSQLModel() throws Exception {
		
		Connection con = null;
		
		// Get session
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//start transaction
		Transaction tx = session.beginTransaction();
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			if (fetchSizeMin) stmt.setFetchSize(Integer.MIN_VALUE);
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = String.format(selectQuery, tableName);
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			int count = 0;
			//System.out.println("EMPIEZO EN LA PRIMERA LINEA");
			while (count<ROWS_TO_READ && rs.next()) {
				
				processRow(session, rs);
				
				count++;
				if (count%100==0) {
					System.out.println(tableName + ": " + count);
					session.flush();
			        session.clear();
				}
				
			}
			
			stmt.close();
			tx.commit();
		} catch (SQLException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			//if (con!=null) con.close();
			HibernateUtil.closeSession();
			HibernateUtil.closeSessionFactory();
		}
	}
	
	protected boolean createdObject(String... attributes) {
		boolean res = false;
		for (String att : attributes) {
			if (att != null) res = true;			
		}
		return res;
	}
	
	protected String nullifyField(String field) {
		return "".equals(field.trim()) 
				|| "n_a".equals(field.trim())
				|| "_".equals(field.trim())
				|| "NULL".equals(field.trim())
				|| "n/a".equals(field.trim())
				|| "N/A".equals(field.trim())
				|| "na".equals(field.trim())
				|| "NA".equals(field.trim())
				|| "-".equals(field.trim())
				? null : field.trim();
	}

}

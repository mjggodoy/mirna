package mirna.mirbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class RellenaMirnaHasPubmedDocument {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public RellenaMirnaHasPubmedDocument() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}
	
	public void execute() throws Exception {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			stmt = (Statement) con.createStatement();
			
			String query = "select * from mirna.mirna_has_pubmed_document";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				int oldPk = rs.getInt("mirna_pk");
				int pmDoc = rs.getInt("pubmed_document_pk");
				
				int newPk = getNewPk(oldPk);

				//insertPkPair(newPk, pmDoc);
				limit--;
				counter++;
				if (counter % 100 == 0) System.out.println(oldPk);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	private int getNewPk(int oldPk) throws Exception {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		int newPk = -1;
		
		try {
			
			stmt = (Statement) con.createStatement();
			
			String query = "select * from mirna.mirna_pk_translation where old_pk="+oldPk;
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int counter = 0;
			
			while (rs.next()) {
				
				newPk = rs.getInt("new_pk");
				counter++;
			}
			
			if (counter!=1) {
				throw new Exception(counter+" nuevos pks encontrados para "+oldPk);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return newPk;
		
	}
	
	public static void main(String[] args) throws Exception {
		RellenaMirnaHasPubmedDocument x = new RellenaMirnaHasPubmedDocument();
		x.execute();
	}

}

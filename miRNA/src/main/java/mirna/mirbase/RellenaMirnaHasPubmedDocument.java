package mirna.mirbase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
	
	private boolean estaYaMetido(int mirnaPk, int pubmedDocumentPk) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean res = false;
		
		try {
			
			String query = "select * from mirna.mirna_has_pubmed_document2 "
					+ "where mirna_pk=? and pubmed_document_pk=?";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, mirnaPk);
			stmt.setInt(2, pubmedDocumentPk);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				res = true;
			}
			
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return res;
		
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
				
				List<Integer> newPks = getNewPks(oldPk);
				//int newPk = getNewPk(oldPk);
				for (int newPk : newPks) {
					if (!estaYaMetido(newPk, pmDoc)) insertPkPair(newPk, pmDoc);
				}
				
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
	
	private void insertPkPair(int mirnaPk, int pubmedDocumentPk) throws SQLException {
		
		String query = "insert into mirna.mirna_has_pubmed_document2 (mirna_pk, pubmed_document_pk) "
				+ "values(?, ?)";
			
		PreparedStatement stmt = null;
		
		try {
		
			stmt = con.prepareStatement(query);
			stmt.setInt(1, mirnaPk);
			stmt.setInt(2, pubmedDocumentPk);
			stmt.execute();
		
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
			
	}
	
	@SuppressWarnings("unused")
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
	
	private List<Integer> getNewPks(int oldPk) throws Exception {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Integer> newPks = new ArrayList<Integer>();
		
		try {
			
			stmt = (Statement) con.createStatement();
			
			String query = "select * from mirna.mirna_pk_translation where old_pk="+oldPk;
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				newPks.add(rs.getInt("new_pk"));
			}
			
			if (newPks.size()==0) {
				throw new Exception("0 nuevos pks encontrados para "+oldPk);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return newPks;
		
	}
	
	public static void main(String[] args) throws Exception {
		RellenaMirnaHasPubmedDocument x = new RellenaMirnaHasPubmedDocument();
		x.execute();
	}

}

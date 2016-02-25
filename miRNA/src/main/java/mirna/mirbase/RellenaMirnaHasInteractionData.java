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

public class RellenaMirnaHasInteractionData {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con1 = null;
	private Connection con2 = null;
	
	public RellenaMirnaHasInteractionData() throws IOException {
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
			
			con1 = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			con2 = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			
			//stmt = (Statement) con.createStatement();
			stmt = con1.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
			String query = "select * from mirna.interaction_data";
			System.out.println("STARTING: " + query);
			
			stmt.setFetchSize(Integer.MIN_VALUE);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				int oldPk = rs.getInt("mirna_pk");
				int edPk = rs.getInt("pk");
				
				List<Integer> newPks = getNewPks(oldPk);
				//int newPk = getNewPk(oldPk);
				for (int newPk : newPks) {
					insertPkPair(newPk, edPk);
				}
				
				limit--;
				counter++;
				if (counter % 100 == 0) System.out.println(counter);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con1!=null) con1.close();
			if (con2!=null) con2.close();
		}
	}
	
	private void insertPkPair(int mirnaPk, int expressionDataPk) throws SQLException {
		
		String query = "insert into mirna.mirna_has_interaction_data2 (mirna_pk, interaction_data_pk) "
				+ "values(?, ?)";
			
		PreparedStatement stmt = null;
		
		try {
		
			stmt = con2.prepareStatement(query);
			stmt.setInt(1, mirnaPk);
			stmt.setInt(2, expressionDataPk);
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
			
			stmt = (Statement) con2.createStatement();
			
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
			
			stmt = (Statement) con2.createStatement();
			
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
		RellenaMirnaHasInteractionData x = new RellenaMirnaHasInteractionData();
		x.execute();
	}

}

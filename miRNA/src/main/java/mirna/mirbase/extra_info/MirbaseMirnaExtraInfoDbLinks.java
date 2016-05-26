package mirna.mirbase.extra_info;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MirbaseMirnaExtraInfoDbLinks {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con1 = null;
	
	public MirbaseMirnaExtraInfoDbLinks() throws IOException {
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
			
			stmt = con1.createStatement();
			
			System.out.println("Querying...");
			String query = "select * from mirbase.mirna_database_links a, mirbase.mirna b "
					+ "where a.auto_mirna = b.auto_mirna;";

			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				System.out.println("Getting from mirbase...");
				String dbId = rs.getString("db_id");
				String dbLink = rs.getString("db_link");
				String dbSecondary = rs.getString("db_secondary");
				String mirnaAcc = rs.getString("mirna_acc");

				
				int mirnaPk = getPk(mirnaAcc);
				
				if (mirnaPk!=-1 && dbSecondary !=null) inserta(nullify(dbId), nullify(dbLink), nullify(dbSecondary), mirnaPk);
				
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
		}
	}
	
	private int getPk(String acc) throws Exception {
		
		Statement stmt = null;
		ResultSet rs = null;
		
		int res = -1;
		
		try {
			
			stmt = (Statement) con1.createStatement();
			
			String query = "select * from mirna.mirna2 where accession_number='"+acc+"'";
			
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int counter = 0;
			
			while (rs.next()) {
				
				res = rs.getInt("pk");
				counter++;
			}
			
			if (counter>1) {
				throw new Exception(counter+" pks encontrados para "+acc);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return res;
		
	}
	
	private void inserta(String dbId, String dbLink, String dbSecondary, int mirnaPk) throws SQLException {
		
		System.out.println("Inserting in mirna_mirbase_database_links table");
		String query = "insert into mirna.mirna_mirbase_database_links (db_id, db_link, db_secondary, mirna_pk) "
				+ "values(?, ?, ?, ?)";
			
		PreparedStatement stmt = null;
		
		try {
		
			stmt = con1.prepareStatement(query);
			stmt.setString(1, dbId);
			stmt.setString(2, dbLink);
			stmt.setString(3, dbSecondary);
			stmt.setInt(4, mirnaPk);
			stmt.execute();
		
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
			
	}
	
	
	public String nullify(String field) {
		String res = field.trim();
		if ("".equals(res)) res = null;
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		
		MirbaseMirnaExtraInfoDbLinks x = new MirbaseMirnaExtraInfoDbLinks();
		x.execute();
	}

}

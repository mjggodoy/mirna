package mirna.mirbase.extra_info;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MirbaseMirnaExtraInfoToUpdate {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con1 = null;
	
	public MirbaseMirnaExtraInfoToUpdate() throws IOException {
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
			String query = "select * from mirbase.mirna2wikipedia a, mirbase.wikipedia b, mirna.mirna2 c"
						+ " where b.auto_id = a.auto_wikipedia and a.auto_mirna = c.mirbase_pk;";
			

			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				System.out.println("get wp_summary");
				String wp_summary = rs.getString("wp_summary");
				System.out.println("get title");
				String title = rs.getString("title");
				System.out.println("get accession_number");
				String acc = rs.getString("accession_number");
				
				System.out.println("get pk");
				int mirnaPk = getPk(acc);
				
				if (mirnaPk!=-1) update(nullify(wp_summary), nullify(title), mirnaPk);
				
				limit--;
				counter++;
				System.out.println(counter);
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
	
	private void update(String wp_summary, String title, int mirnaPk) throws SQLException {
		
			
		Statement stmt = null;
	
		try {
			
				System.out.println("updating");				
				PreparedStatement ps = con1.prepareStatement(
						"update mirna.mirna_mirbase_info SET wp_description=?, wp_title=? where " +
						"mirna_pk=?");
				 ps.setString(1,wp_summary);
				 ps.setString(2,title);
				 ps.setInt(3,mirnaPk);
				 ps.executeUpdate();
				 ps.close();
				
				
			
			
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
		MirbaseMirnaExtraInfoToUpdate x = new MirbaseMirnaExtraInfoToUpdate();
		x.execute();
	}

}
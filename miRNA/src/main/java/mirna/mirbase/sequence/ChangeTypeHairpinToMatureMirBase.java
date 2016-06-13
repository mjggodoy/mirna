package mirna.mirbase.sequence;

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

/**
 * Created by Esteban on 23/05/2016.
 */
public class ChangeTypeHairpinToMatureMirBase {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public ChangeTypeHairpinToMatureMirBase() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void execute() throws Exception {

		
		Statement stmt = null;
		ResultSet rs = null;
		//boolean res;		


		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 where mirbase_pk is null and id like '%-3p';";
			//String query = "select * from mirna.mirna2 where mirbase_pk is null and id like '%-5p';";

			System.out.println("STARTING: " + query);

			rs = stmt.executeQuery(query);


			int limit = -1;
			
			int count = 0;
			while (rs.next() && limit!=0) {
				

				int pk = rs.getInt("pk"); // pk del mirna2.
				update(pk);
				count++;
				System.out.println("Iteraciones: " + count);
					
			}
			

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	
	private void update(int mirnaPk) throws SQLException {
		
		
		Statement stmt = null;
	
		try {
			
				System.out.println("updating");				
				PreparedStatement ps = con.prepareStatement(
						"update mirna.mirna2 SET type = 'mature' where pk=? and type='hairpin'");
				 ps.setInt(1,mirnaPk);
				 ps.executeUpdate();
				 ps.close();
				
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}			
		
			
	}
	
	
	public static void main(String[] args) throws Exception {
		ChangeTypeHairpinToMatureMirBase x = new ChangeTypeHairpinToMatureMirBase();
		x.execute();
	}


}

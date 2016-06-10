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
public class ChangeTypeMatureHairpinFromOldMirna {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public ChangeTypeMatureHairpinFromOldMirna() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void execute() throws Exception {

		
		Statement stmt = null;
		ResultSet rs = null;
		List<String> res = new ArrayList<String>();


		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 a where a.mirbase_pk is null;";
			System.out.println("STARTING: " + query);

			rs = stmt.executeQuery(query);


			int limit = -1;
			
			int count = 0;
			while (rs.next() && limit!=0) {
				

				int pk = rs.getInt("pk"); // pk del mirna2.
				res = comprobarMature(pk);
				
				for(String pk_new : res){
					
					int pk_method = Integer.valueOf(pk_new);
					System.out.println("pk_method" + pk_method);
					
					//update(pk_method);
					count++;
					System.out.println(count);
					
				}
				
			}
			

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	
	private List<String> comprobarMature(int pk) throws SQLException{
		
		
		Statement stmt = null;
		ResultSet rs = null;
		List<String> res = new ArrayList<String>();

		
		try{
		
		stmt = con.createStatement();

		String query = "select * from mirna.mirna2 a, mirna.mirna_pk_translation b, mirna.mirna_has_mature c "
				+ "where a.pk ="+ pk +" and a.pk = b.new_pk and b.old_pk = c.mature_pk";
		
		rs = stmt.executeQuery(query);
		
		
		while(rs.next()){
			
			int new_pk = rs.getInt("new_pk");
			res.add(Integer.toString(new_pk));
			System.out.println("new_pk "+ new_pk);
			
		}
		
		
		}catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		
		return res;
	}
	
	
	private void update(int mirnaPk) throws SQLException {
		
		
		Statement stmt = null;
	
		try {
			
				System.out.println("updating");				
				PreparedStatement ps = con.prepareStatement(
						"update mirna.mirna2 SET type = 'mature' where pk=?");
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
		ChangeTypeMatureHairpinFromOldMirna x = new ChangeTypeMatureHairpinFromOldMirna();
		x.execute();
	}


}

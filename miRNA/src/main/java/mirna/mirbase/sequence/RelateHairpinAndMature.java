package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created by Esteban on 23/05/2016.
 */
public class RelateHairpinAndMature {

	private class FromTo {
		public int from;
		public int to;
	}

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public RelateHairpinAndMature() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void execute() throws Exception {
		
		ArrayList<String> hairpin = getHairpinIds();
		ArrayList<String> mature = getMatureIds();
		System.out.println("size hairpin: "+ hairpin.size());
		System.out.println("size mature: "+ mature.size());

		ArrayList<String> hairpin_mature = new ArrayList<String>();

		int count_mature = 0;
		int count_hairpin = 0;
		
		for(String id_hairpin: hairpin){
			
			//System.out.println("id_hairpin: " + id_hairpin);
			
			for(String id_mature: mature){
				
				//System.out.println("id_hairpin: " + id_mature);
				
				if(id_hairpin.contains(id_mature)){
					
					hairpin_mature.add(id_mature+"\t"+ id_hairpin);

					
				}
				
				count_mature++;
				
			}
			
			count_hairpin++;
		}		
		
		System.out.println("count hairpin: " + count_hairpin);
		System.out.println("count mature: " + count_mature);

		for (String id_mature : hairpin_mature) {
			
			int pk_hairpin = getHairpinPk(id_mature);
			int pk_mature = getMaturePk(id_mature);
			System.out.println(id_mature + " " + "Hairpin: " + pk_hairpin + " " +  "Mature: " + pk_mature);
			
			
		}
		
		System.out.println("size: " + hairpin_mature.size());	
	}

	private ArrayList<String> getHairpinIds() throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> res = new ArrayList<String>(); 

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 a, mirna.sequence_hairpin b"
					+ " where a.mirbase_pk is null and a.pk = b.mirna_pk;";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				String id_mirna = rs.getString("id");				
				res.add(id_mirna);

				limit--;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
		
		return res;
	}

	
	private ArrayList<String> getMatureIds() throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<String> res = new ArrayList<String>(); 

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 a, mirna.sequence_mature b "
					+ "where a.mirbase_pk is null and a.pk = b.mirna_pk;";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				String id_mirna = rs.getString("id");
				int pk = rs.getInt("pk");

				res.add(id_mirna);

				limit--;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
		
		return res;
	}
	
	private int getHairpinPk(String id) throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;
		int pk_hairpin = 0;

		

		StringTokenizer st = new StringTokenizer(id, "\t");
	    
		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();
			
			while (st.hasMoreTokens()) {
		     

				String query = "select * from mirna.mirna2 a, mirna.sequence_hairpin b "
						+ "where a.mirbase_pk is null and a.pk = b.mirna_pk and id like '"+ st.nextToken() +"'";
				System.out.println("STARTING: " + query);
	


			// execute the query, and get a java resultset
				rs = stmt.executeQuery(query);

				int limit = -1;

				while (rs.next() && limit!=0) {

					pk_hairpin = rs.getInt("pk");

				limit--;
			}
			
				
			
			 }

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
		
		return pk_hairpin ;
	}

	
	private int getMaturePk(String id) throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;
		int pk_mature = 0;

		

		StringTokenizer st = new StringTokenizer(id, "\t");
	    
		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = con.createStatement();
			
			while (st.hasMoreTokens()) {
		     

				String query = "select * from mirna.mirna2 a, mirna.sequence_mature b "
						+ "where a.mirbase_pk is null and a.pk = b.mirna_pk and id like '"+ st.nextToken() +"'";
				System.out.println("STARTING: " + query);
	


			// execute the query, and get a java resultset
				rs = stmt.executeQuery(query);

				int limit = -1;

				while (rs.next() && limit!=0) {

					pk_mature= rs.getInt("pk");

				limit--;
			}
			
				
			
			 }

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
		
		return pk_mature ;
	}

	
	
	
	
	
	/*private void inserta() throws SQLException {

		String query = "insert into mirna.hairpin2mature (hairpin_pk, mature_pk) "
				+ "values(?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, sequence);
			stmt.setInt(2, mirnaPk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}

*/
	public static void main(String[] args) throws Exception {
		RelateHairpinAndMature x = new RelateHairpinAndMature();
		x.execute();
	}


}

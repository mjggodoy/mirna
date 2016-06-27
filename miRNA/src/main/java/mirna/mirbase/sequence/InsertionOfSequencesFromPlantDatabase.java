package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class InsertionOfSequencesFromPlantDatabase {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertionOfSequencesFromPlantDatabase() throws IOException {
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

			stmt = con.createStatement();

			String query = "select * from mirna_raw.plant_mirna_stem_loop;";
			System.out.println("STARTING: " + query);


			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				String specie = rs.getString("specie");
				String mirna_id = rs.getString("mirna_id");
				String new_name = specie+"-"+mirna_id.toLowerCase();
				String new_name2 = specie+"-"+mirna_id;

				
				String new_name3 = new_name.toLowerCase();
				new_name3 = new_name3.substring(0,6) + Character.toString(new_name3.charAt(6)).toUpperCase() +new_name3.substring(7);

				String new_name4 = new_name.toUpperCase();
				new_name4 = new_name4.substring(0,3).toLowerCase() + new_name4.substring(3);

			
				String new_name5 = new_name.toUpperCase();
				new_name5 = new_name4.substring(0,3).toLowerCase() + new_name4.substring(3,9) + new_name4.substring(9).toLowerCase(); ;
				
				System.out.println(new_name + " " + new_name2 + " " + new_name3 + " " + new_name4 + " " + new_name5 );

					if(findDatabase(new_name) && findDatabase(new_name2) && findDatabase2(new_name3) && findDatabase2(new_name4) && findDatabase2(new_name5)){
						
						System.out.println(new_name+" no encontrado para pk: "+pk);
						//throw new Exception(new_name +" or " + new_name2 +" encontrado para pk: "+pk);
						
						//String sequence = rs.getString("sequence");

						
					}
				
				limit--;
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}

	private int getRealPk(int pk) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		int res = 0;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna_pk_translation where old_pk="+pk;


			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {

				res = rs.getInt("new_pk");
				counter++;
				//System.out.println(counter);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return res;

	}

	private boolean findDatabase(String mirna) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		String res = null;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2;";


			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {

				res = rs.getString("id");
				counter++;
				
				if (res.equals(mirna)) {
					
					Integer mirbase = rs.getInt("mirbase_pk");
					
					if(mirbase == null)
						
						return true;
					
				}

				//System.out.println(counter);
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return false;

	}
	
	private boolean findDatabase2(String mirna) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		String res = null;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2;";


			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {

				res = rs.getString("previous_id");
				counter++;
				
				if (res != null && res.equals(mirna)) {
					
					Integer mirbase = rs.getInt("mirbase_pk");
					
					if(mirbase == null)
						
						return true;
					
				}

				//System.out.println(counter);
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return false;

	}
	
	
	private void inserta(String sequence, int mirnaPk) throws SQLException {

		String query = "insert into mirna.sequence_hairpin (sequence, mirna_pk) "
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

	public static void main(String[] args) throws Exception {
		
		InsertionOfSequencesFromPlantDatabase plants = new InsertionOfSequencesFromPlantDatabase();
		plants.execute();
		
		
	}


}

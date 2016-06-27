package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Maria on 23/05/2016.
 */
public class InsertMatureSequencesFromOtherDatabases2 {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertMatureSequencesFromOtherDatabases2() throws IOException {
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

			String query = "select * from mirna.mirna2 where mirbase_pk is NULL and type='mature'";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				String sequence = getSequence(pk);
				
				
				System.out.println("Sequence: " + sequence +" // "+ "pk: " + pk);
				
				if(sequence != null){
				
					inserta(sequence, pk);

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

	
	
	private String getSequence(int mirnaPk) throws SQLException {
		
		
		Statement stmt = null;
		ResultSet rs = null;
		String sequence = null;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna_pk_translation a, mirna.mature_has_sequence b, mirna.sequence c "
					+ "where a.new_pk="+ mirnaPk+" and a.old_pk = b.mature_pk and b.sequence_pk = c.pk and c.sequence is not null"; 

			System.out.println(query);
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {

				sequence = rs.getString("sequence");
				counter++;
				System.out.println(sequence);
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return sequence;

	}

	private void inserta(String sequence, int mirnaPk) throws SQLException {
		
		System.out.println("Inserting...");

		String query = "insert into mirna.sequence_mature (sequence, mirna_pk) "
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
		InsertMatureSequencesFromOtherDatabases2 x = new InsertMatureSequencesFromOtherDatabases2();
		x.execute();
	}


}

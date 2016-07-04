package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Maria on 23/05/2016.
 */
public class InsertMatureSeqsfromOtherDatabases2 {


	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertMatureSeqsfromOtherDatabases2() throws IOException {
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

			String query = "select * from mirna.mirna2 a, mirna.mirna_pk_translation b, mirna_old.mirna_has_mature c, mirna_old.mature_has_sequence d, mirna_old.sequence e "
					+ "where  a.mirbase_pk is null and a.type = 'mature' and a.pk = b.new_pk and b.old_pk = c.mirna_pk and c.mature_pk = d.mature_pk and d.sequence_pk = e.pk ;";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;
			int count = 0;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				String sequence = rs.getString("sequence");
				System.out.println("pk: " + pk + " " + "sequence: " + sequence + " Count: "+ count);
				inserta(pk, sequence);
				count ++;
				limit--;
			}

			System.out.println(count);
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}

	
	
	private void inserta(int pk_mirna, String sequence) throws SQLException {

		String query = "insert into mirna.sequence_mature (mirna_pk, sequence) "
				+ "values(?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setInt(1, pk_mirna);
			stmt.setString(2, sequence);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}


	public static void main(String[] args) throws Exception {
		InsertMatureSeqsfromOtherDatabases2 x = new InsertMatureSeqsfromOtherDatabases2();
		x.execute();
	}


}

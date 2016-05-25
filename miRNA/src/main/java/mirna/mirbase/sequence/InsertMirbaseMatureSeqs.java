package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Esteban on 23/05/2016.
 */
public class InsertMirbaseMatureSeqs {

	private class FromTo {
		public int from;
		public int to;
	}

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertMirbaseMatureSeqs() throws IOException {
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

			String query = "select * from mirna.mirna2 where mirbase_pk is not NULL and type='mature'";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				//String acc = rs.getString("mirna_acc");
				int pk = rs.getInt("pk");
				int mirbasePk = rs.getInt("mirbase_pk");
				FromTo sequence = getSequence(mirbasePk);
				System.out.println(sequence.from + " - " + sequence.to);
				System.out.println(pk);


				// HACER COSITAS
				inserta(sequence.from, sequence.to, pk);

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

	private FromTo getSequence(int pk) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		FromTo res = null;

		try {

			stmt = con.createStatement();

			String query = "select * from mirbase.mirna_pre_mature where auto_mature="+pk;


			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {
				res = new FromTo();
				res.from = rs.getInt("mature_from");
				res.to = rs.getInt("mature_to");
				counter++;
			}

			if (counter>1) {
				throw new Exception(counter+" pks encontrados para "+pk);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return res;

	}

	private void inserta(int from, int to, int mirnaPk) throws SQLException {

		String query = "insert into mirna.sequence_mature (from_idx, to_idx, mirna_pk) "
				+ "values(?, ?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setInt(1, from);
			stmt.setInt(2, to);
			stmt.setInt(3, mirnaPk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}

	public static void main(String[] args) throws Exception {
		InsertMirbaseMatureSeqs x = new InsertMirbaseMatureSeqs();
		x.execute();
	}


}

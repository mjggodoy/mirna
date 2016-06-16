package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Esteban on 23/05/2016.
 */
public class InsertMatureSeqsfromOtherDatabases {

	private class FromTo {
		public int from;
		public int to;
	}

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertMatureSeqsfromOtherDatabases() throws IOException {
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

			String query = "select * from mirna.hairpin_has_sequence a, mirna.sequence b, mirna.mirna c "
					+ "where c.pk = a.hairpin_pk and a.sequence_pk = b.pk";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				String sequence = rs.getString("sequence");
				String gc_proportion = rs.getString("gc_proportion");
				String length = rs.getString("length");
				int pk_mirna2 = getNewPkfromMirna2(pk);
				System.out.println("pk: "+ pk +" pk_mirna2: " + pk_mirna2 + " sequence: " + sequence + " gc_proportion: " +gc_proportion + " length: " + length );
				inserta(pk_mirna2, sequence, gc_proportion,length);

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

	private int getNewPkfromMirna2(int pk) throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;

		int res = -1;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna_pk_translation a where a.old_pk="+pk;

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {
				
				res = rs.getInt("new_pk");
				counter++;
				System.out.println("Number of new pks: " + counter);
				
				/*if(counter > 1){
					
					throw new Exception(" pk= "+res);
				}*/
				
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return res;

	}

	
	private void inserta(int pk_mirna2, String sequence, String gc_proportion, String length) throws SQLException {

		String query = "insert into mirna.sequence_mature (pk, sequence, gc_proportion, length) "
				+ "values(?, ?, ?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setInt(1, pk_mirna2);
			stmt.setString(2, sequence);
			stmt.setString(3, gc_proportion);
			stmt.setString(4, length);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}


	public static void main(String[] args) throws Exception {
		InsertMatureSeqsfromOtherDatabases x = new InsertMatureSeqsfromOtherDatabases();
		x.execute();
	}


}

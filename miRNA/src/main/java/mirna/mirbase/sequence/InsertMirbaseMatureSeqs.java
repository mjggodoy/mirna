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

			String query = "select * from mirna.hairpin2mature";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				//String acc = rs.getString("mirna_acc");
				int hairpinPk = rs.getInt("hairpin_pk");
				int maturePk = rs.getInt("mature_pk");
				int autoMirna = getMirbasePk(hairpinPk);
				int autoMature = getMirbasePk(maturePk);
				FromTo sequence = getSequence(autoMirna, autoMature);
				System.out.println(sequence.from + " - " + sequence.to);

				update(sequence.from, sequence.to, hairpinPk, maturePk);

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

	private int getMirbasePk(int pk) throws Exception  {

		Statement stmt = null;
		ResultSet rs = null;

		int res = -1;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 where pk="+pk;

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int counter = 0;

			while (rs.next()) {
				res = rs.getInt("mirbase_pk");
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

	private FromTo getSequence(int hairpinPk, int maturePk) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		FromTo res = null;

		try {

			stmt = con.createStatement();

			String query = "select * from mirbase.mirna_pre_mature where auto_mature="
					+maturePk+" and auto_mirna="+hairpinPk;

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
				throw new Exception(counter+" pks encontrados para "+maturePk+" y "+hairpinPk);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return res;

	}

	private void update(int from, int to, int hairpinPk, int maturePk) throws SQLException {

		String query = "update mirna.hairpin2mature set from_idx=?, to_idx=? "
				+ "where hairpin_pk=? and mature_pk=?";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setInt(1, from);
			stmt.setInt(2, to);
			stmt.setInt(3, hairpinPk);
			stmt.setInt(4, maturePk);
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

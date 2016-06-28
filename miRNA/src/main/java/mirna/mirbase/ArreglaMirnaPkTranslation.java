package mirna.mirbase;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Esteban on 27/06/2016.
 */
public class ArreglaMirnaPkTranslation {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public ArreglaMirnaPkTranslation() throws IOException {
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("MiRna-mysql.properties"));
		this.dbUrl = props.getProperty("url");
		this.dbUser = props.getProperty("user");
		this.dbPassword = props.getProperty("password");
	}

	public void setMatures() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		List<Integer> pks = new ArrayList<Integer>();

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = (Statement) con.createStatement();

			String query = "select a.pk from mirna.mirna2 a, mirna.mirna_pk_translation b, mirna_old.mirna c where a.pk=b.new_pk and b.old_pk=c.pk and c.arm is not null and a.mirbase_pk is null";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;
			int counter = 0;

			while (rs.next() && limit!=0) {
				pks.add(rs.getInt("pk"));
			}

			System.out.println(pks.size());

			for (int pk : pks) {
				setTypeToMature(pk);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}

	private void setTypeToMature(int pk) throws Exception {

		String query = "update mirna.mirna2 set type=? "
				+ "where pk=?";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, "mature");
			stmt.setInt(2, pk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
	}

	public void setArmsNotMirbase() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = (Statement) con.createStatement();

			String query = "select a.pk, c.arm from mirna.mirna2 a, mirna.mirna_pk_translation b, mirna_old.mirna c where a.pk=b.new_pk and b.old_pk=c.pk and c.arm is not null and a.mirbase_pk is null";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;
			int counter = 0;

			while (rs.next() && limit!=0) {
				int pk = rs.getInt("pk");
				String arm = rs.getString("arm");
				setArmToMature(pk, arm);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}

	private void setArmToMature(int pk, String arm) throws Exception {

		String query = "update mirna.mirna2 set arm=? "
				+ "where pk=?";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, arm);
			stmt.setInt(2, pk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
	}

	public void recorreMirnaConArmNotNull() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		List<Integer> pks = new ArrayList<Integer>();

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = (Statement) con.createStatement();

			String query = "select * from mirna_old.mirna where arm is not null";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {
				int oldPk = rs.getInt("pk");
				String arm = rs.getString("arm");
				List<QueryResult> mirnas = getTranslatedMirnas(oldPk);
				int hairpins = 0;
				int matures = 0;
				for (QueryResult o : mirnas) {
					if (o.type.equals("hairpin")) hairpins++;
					if (o.type.equals("mature")) matures++;
				}
				boolean res = false;


				if (matures==1 && (hairpins==0 || hairpins==1)) {
					//ACTUALIZAR MATURE
					res = true;
				} else  if ((matures == 0) && (hairpins==0)) {
					// ALL OK
					res = true;

				} else if ((matures>0) && (hairpins==0)) {
					res = searchArmInMatures(mirnas, arm);
				} else if ((matures==0) && (hairpins==1)) {
					res = searchArmInMatures(getMatures(mirnas.get(0).pk), arm);
				}

				if (!res) {
					System.err.println("Comprobar pk = "+oldPk+" hairpins("+hairpins+") matures("+matures+")");
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

	private List<QueryResult> getTranslatedMirnas(int oldPk) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<QueryResult> res = new ArrayList<>();

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			String query = "select a.pk, a.id, a.type from mirna.mirna2 a, mirna.mirna_pk_translation b where a.pk=b.new_pk and b.old_pk=? and a.mirbase_pk is not null";

			// execute the query, and get a java resultset
			stmt = con.prepareStatement(query);
			stmt.setInt(1, oldPk);
			rs = stmt.executeQuery();

			int limit = -1;
			int counter = 0;

			while (rs.next() && limit!=0) {
				QueryResult q = new QueryResult();
				q.pk = rs.getInt("pk");
				q.id = rs.getString("id");
				q.type = rs.getString("type");
				res.add(q);
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

	private List<QueryResult> getMatures(int pk) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List<QueryResult> res = new ArrayList<>();

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			String query = "select c.* from mirna.hairpin2mature b, mirna.mirna2 c where b.hairpin_pk=? and b.mature_pk=c.pk;";

			// execute the query, and get a java resultset
			stmt = con.prepareStatement(query);
			stmt.setInt(1, pk);
			rs = stmt.executeQuery();

			int limit = -1;
			int counter = 0;

			while (rs.next() && limit!=0) {
				QueryResult q = new QueryResult();
				q.pk = rs.getInt("pk");
				q.id = rs.getString("id");
				q.type = rs.getString("type");
				res.add(q);
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

	private boolean searchArmInMatures(List<QueryResult> matures, String arm) {
		int counter=0;
		for (QueryResult m : matures) {
			if (m.id.contains(arm)) {
				counter++;
			}
		}
		return counter==1;
	}

	private class QueryResult {
		int pk;
		String id;
		String type;
	}

	public static void main(String[] args) throws Exception {
		ArreglaMirnaPkTranslation x = new ArreglaMirnaPkTranslation();
		//x.setMatures();
		//x.setArmsNotMirbase();
		x.recorreMirnaConArmNotNull();
	}


}

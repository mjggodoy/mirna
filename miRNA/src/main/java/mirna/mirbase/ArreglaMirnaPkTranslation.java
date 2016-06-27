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

	public void execute() throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			stmt = (Statement) con.createStatement();

			String query = "select * from mirna_old.mirna where arm is not null";
			System.out.println("STARTING: " + query);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			int limit = -1;
			int counter = 0;

			while (rs.next() && limit!=0) {

				int oldPk = rs.getInt("pk");
				String arm = rs.getString("arm");

				String mirna = buscaNuevo(oldPk);
				if (!mirna.contains(arm)) {
					mirna = buscaMejorNuevo();
				}
				syso(name + "-" + mirna);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}

	private String buscaNuevo(int oldPk) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {

			String query = "select id from mirna.mirna2 "
					+ "where "+column+" like '"+oldPk+",%' "
					+ "or "+column+" like '%,"+oldPk+",%'";
			stmt = con.prepareStatement(query);
//			stmt.setInt(1, oldPk);
//			stmt.setInt(2, oldPk);

			// execute the query, and get a java resultset
			rs = stmt.executeQuery();

			while (rs.next()) {
				newPks.add(rs.getInt("pk"));
			}

			if (newPks.size()==0) { // TODO: Chequear >1
				if (buscaEnPre) {
					String exMsg = newPks.size()+" nuevos pks encontrados para oldPk="+oldPk+"\n"+query;
					throw new Exception(exMsg);
				} else {
					newPks = buscaNuevoPk(oldPk, true);
				}


			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return newPks;
	}


}

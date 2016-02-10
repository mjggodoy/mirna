package mirna.mirbase;

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

public class MirnaPkTranslation {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public MirnaPkTranslation() throws IOException {
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
			
			String query = "select * from mirna.mirna";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			int counter = 0;
			
			while (rs.next() && limit!=0) {
				
				int oldPk = rs.getInt("pk");
				List<Integer> newPks = buscaNuevoPk(oldPk);
//				System.out.println(oldPk+" -> "+newPks);
				insertPkPair(oldPk, newPks);
				limit--;
				counter++;
				if (counter % 100 == 0) System.out.println(oldPk);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	private List<Integer> buscaNuevoPk(int oldPk) throws Exception {
		return buscaNuevoPk(oldPk, false);
	}
	
	private List<Integer> buscaNuevoPk(int oldPk, boolean buscaEnPre) throws Exception {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		List<Integer> newPks = new ArrayList<Integer>();
		String column = "old_pks";
		if (buscaEnPre) column = "pre_old_pks";
		
		try {
			
			String query = "select * from mirna.mirna2 "
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
	
	private void insertPkPair(int oldPk, List<Integer> newPks) throws SQLException {
		
		String query = "insert into mirna.mirna_pk_translation (old_pk, new_pk) "
				+ "values(?, ?)";
			
		for (int newPk : newPks) {
			
			PreparedStatement stmt = null;
			
			try {
			
				stmt = con.prepareStatement(query);
				stmt.setInt(1, oldPk);
				stmt.setInt(2, newPk);
				
//				System.out.println(stmt);
				
				// execute the query
				stmt.execute();
			
			} catch (SQLException e) {
				throw e;
			} finally {
				if (stmt!=null) stmt.close();
			}
			
		}
	}
	
	public static void main(String[] args) throws Exception {
		MirnaPkTranslation x = new MirnaPkTranslation();
		x.execute();
	}

}

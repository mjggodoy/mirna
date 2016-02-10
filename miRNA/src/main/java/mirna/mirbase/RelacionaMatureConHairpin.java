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

import mirna.mirbase.bean.HairpinPks;

public class RelacionaMatureConHairpin {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public RelacionaMatureConHairpin() throws IOException {
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
			
			String query = "select * from mirna.mirna2 where mature=true";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			
			
			while (rs.next() && limit!=0) {
				
				int maturePk = rs.getInt("pk");
//				System.out.println("Processing pk="+pk);
				
				int matureAuto = rs.getInt("mirbase_pk");
				
				List<HairpinPks> hairpins = getHairpinFromMature(matureAuto);
				
				if (hairpins.size()==0) System.err.println("Mature "+maturePk + " no tiene hairpins??");
				
				insertHairpinMaturePair(maturePk, hairpins);
				
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
	
	
	private List<HairpinPks> getHairpinFromMature(int maturePk) throws SQLException {
		List<HairpinPks> hairpinPks = new ArrayList<HairpinPks>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "select * from mirbase.mirna_pre_mature a, mirna.mirna2 b "
					+ "where a.auto_mature=? and a.auto_mirna=b.mirbase_pk and b.mature=false";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, maturePk);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int hairpinAuto = rs.getInt("auto_mirna");
				int hairpinPk = rs.getInt("pk");
				hairpinPks.add(new HairpinPks(hairpinAuto, hairpinPk));	
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return hairpinPks;
		
	}
	
	private void insertHairpinMaturePair(int maturePk, List<HairpinPks> hairpins) throws SQLException {
		
		String query = "insert into mirna.hairpin2mature (hairpin_pk, mature_pk) "
				+ "values(?, ?)";
			
		for (HairpinPks hairpin : hairpins) {
			
			PreparedStatement stmt = null;
			
			try {
			
				stmt = con.prepareStatement(query);
				stmt.setInt(1, hairpin.getPk());
				stmt.setInt(2, maturePk);
				
				System.out.println(stmt);
				
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
		RelacionaMatureConHairpin x = new RelacionaMatureConHairpin();
		x.execute();
	}

}

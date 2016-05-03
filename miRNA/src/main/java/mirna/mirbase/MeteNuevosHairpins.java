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

import mirna.mirbase.bean.Mirna;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MeteNuevosHairpins {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public MeteNuevosHairpins() throws IOException {
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
				
				int matureAuto = rs.getInt("mirbase_pk");
				
				List<Integer> hairpins = getHairpinFromMature(matureAuto);
				
//				if (hairpins.size()!=0) {
//					System.err.println("Mature "+maturePk + " no tiene hairpins??");
//					
//					System.out.println("PARA MATURE ("+matureAuto+") encontrados estos hairpins: "+hairpins);
//					limit=1;
//					
//				}
				
				for (int hairpinAuto : hairpins) {
					
					Mirna hairpin = getMirna(hairpinAuto);
					insertIntoMirnaNew(hairpin.getId(), hairpin.getAcc(), hairpin.getPreviousId(), false,
							false, hairpin.getMirbasePk());
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
	
	private List<Integer> getHairpinFromMature(int maturePk) throws SQLException {
		List<Integer> allHairpins = new ArrayList<Integer>();
		List<Integer> existingHairpins = new ArrayList<Integer>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "select * from mirbase.mirna_pre_mature where auto_mature=?";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, maturePk);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int hairpinAuto = rs.getInt("auto_mirna");
				allHairpins.add(hairpinAuto);	
			}
			
			rs.close();
			stmt.close();
			
			query = "select * from mirbase.mirna_pre_mature a, mirna.mirna2 b "
					+ "where a.auto_mature=? and a.auto_mirna=b.mirbase_pk and b.mature=false";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, maturePk);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int hairpinAuto = rs.getInt("auto_mirna");
				existingHairpins.add(hairpinAuto);	
			}
			
			allHairpins.removeAll(existingHairpins);
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return allHairpins;
		
	}
	
	private Mirna getMirna(int autoMirna) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Mirna mirna = null;
		
		try {
			
			String query = "select * from mirbase.mirna where auto_mirna=?";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, autoMirna);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			int counter = 0;
			
			while (rs.next()) {
				
				int mirbasePk = rs.getInt("auto_mirna");
				String acc = cleanField(rs.getString("mirna_acc"));
				String mirnaId = cleanField(rs.getString("mirna_id"));
				String previousMirnaId = cleanField(rs.getString("previous_mirna_id"));
				String description = cleanField(rs.getString("description"));
				String sequence = cleanField(rs.getString("sequence"));
				String comment = cleanField(rs.getString("comment"));
				
				mirna = new Mirna(acc, mirnaId, previousMirnaId, mirbasePk, description, sequence, comment);
				counter++;
			}
			
			if (counter!=1) throw new Exception(counter + " mirnas encontrados para id="+autoMirna);
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return mirna;
		
	}
	
	private String cleanField(String field) {
		if (field!=null) {
			field = field.trim();
			if (field.equals("")) field = null;
		}
		return field;
	}
	
	private void insertIntoMirnaNew(String id, String acc, String preId, boolean mature,
			boolean dead, Integer mirbasePk) throws SQLException {
		PreparedStatement stmt = null;
		
		try {
			
			String query = "insert into mirna.mirna2 (id, accession_number,"
					+ "previous_id, mature, dead, mirbase_pk, old_pks, pre_old_pks) "
					+ "values(?, ?, ?, ?, ?, ?, ?, ?)";
//				if (pre) query += "pre_old_pks";
//				else query += "old_pks";
//				query += ") values(?, ?, ?, ?, ?, ?, ?)";
			
			stmt = con.prepareStatement(query);
			stmt.setString(1, id);
			stmt.setString(2, acc);
			stmt.setString(3, preId);
			stmt.setBoolean(4, mature);
			stmt.setBoolean(5, dead);
			stmt.setInt(6, mirbasePk);
			stmt.setString(7, "");
			stmt.setString(8, "");
			
			System.out.println(stmt);
			
			// execute the query
			stmt.execute();
			
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.err.println("Se intentó meter a: id="+id+", acc="+acc+", preId="+preId+", mature="+mature+", dead="+dead+", mirbasePk="+mirbasePk);
			throw e;	
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		MeteNuevosHairpins x = new MeteNuevosHairpins();
		x.execute();
	}

}

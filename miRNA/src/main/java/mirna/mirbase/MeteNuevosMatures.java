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

import mirna.mirbase.bean.Mature;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MeteNuevosMatures {
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	
	private Connection con = null;
	
	public MeteNuevosMatures() throws IOException {
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
			
			String query = "select * from mirna.mirna2 where mature=false and dead=false";
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			
			
			while (rs.next() && limit!=0) {
				
				int hairpinAuto = rs.getInt("mirbase_pk");
				
				List<Integer> matures = getMatureFromHairpin(hairpinAuto);
				
//				if (matures.size()!=0) {
//					
//					System.out.println("PARA HAIRPIN ("+hairpinAuto+") encontrados estos matures: "+matures);
//					limit=1;
//					
//				}
				
				for (int matureAuto : matures) {
					
					Mature mature = getMature(matureAuto);
					insertIntoMirnaNew(mature.getId(), mature.getAcc(), mature.getPreviousId(), true,
							false, mature.getMirbasePk());
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
	
	private List<Integer> getMatureFromHairpin(int hairpinPk) throws SQLException {
		List<Integer> allMatures = new ArrayList<Integer>();
		List<Integer> existingMatures = new ArrayList<Integer>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			String query = "select * from mirbase.mirna_pre_mature where auto_mirna=?";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, hairpinPk);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int matureAuto = rs.getInt("auto_mature");
				allMatures.add(matureAuto);	
			}
			
			rs.close();
			stmt.close();
			
			query = "select * from mirbase.mirna_pre_mature a, mirna.mirna2 b "
					+ "where a.auto_mirna=? and a.auto_mature=b.mirbase_pk and b.mature=true";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, hairpinPk);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int matureAuto = rs.getInt("auto_mature");
				existingMatures.add(matureAuto);	
			}
			
			allMatures.removeAll(existingMatures);
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return allMatures;
		
	}
	
	private Mature getMature(int autoMature) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Mature mature = null;
		
		try {
			
			String query = "select * from mirbase.mirna_mature where auto_mature=?";
			
			stmt = con.prepareStatement(query);
			stmt.setInt(1, autoMature);
			
			// execute the query, and get a java resultset
			rs = stmt.executeQuery();
			
			int counter = 0;
			
			while (rs.next()) {
				
				int mirbasePk = rs.getInt("auto_mature");
				String acc = cleanField(rs.getString("mature_acc"));
				String mirnaId = cleanField(rs.getString("mature_name"));
				String previousMirnaId = cleanField(rs.getString("previous_mature_id"));
				String evidence = cleanField(rs.getString("evidence"));
				String experiment = cleanField(rs.getString("experiment"));
				String similarity = cleanField(rs.getString("similarity"));
				
				mature = new Mature(acc, mirnaId, previousMirnaId, mirbasePk, evidence, experiment, similarity);
				counter++;
			}
			
			if (counter!=1) throw new Exception(counter + " matures encontrados para id="+autoMature);
				
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}
		
		return mature;
		
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
		MeteNuevosMatures x = new MeteNuevosMatures();
		x.execute();
	}

}

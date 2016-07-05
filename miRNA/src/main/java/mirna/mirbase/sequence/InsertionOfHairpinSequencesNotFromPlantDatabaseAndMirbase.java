package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import mirna.integration.database.NewMirnaDatabase;


public class InsertionOfHairpinSequencesNotFromPlantDatabaseAndMirbase {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertionOfHairpinSequencesNotFromPlantDatabaseAndMirbase() throws IOException {
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

			String query = "select * from mirna_old.mirna_has_hairpin a, mirna_old.hairpin_has_sequence b, mirna_old.sequence c "
					+ " where a.hairpin_pk = b.hairpin_pk and b.sequence_pk = c.pk;";
			System.out.println("STARTING: " + query);

			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				if (pk%500==0) System.out.println(pk);
				int mirna_pk = rs.getInt("mirna_pk");
				String sequence = rs.getString("sequence");
				mirna_pk = translationToMirna(mirna_pk);

				List<MirnaResult> list = findDatabase(mirna_pk);
				
				if (list==null) {
					
					System.out.println("La list es nula");
				
				} else if (list.size()==0) {

					//HAY QUE INSERTAR
					System.out.println("El tama–o de la lista es 1, hay que insertar secuencia: " + sequence + mirna_pk);	
					inserta(sequence, mirna_pk);	
					
				} else if (list.size()==1) {
					
					//NO HAY QUE INSERTAR NADA
					System.out.println("La secuencia ya est‡ inclu’da");
						
				} else {
					throw new Exception("Encontrados "+list.size()+" para "+sequence+"-"+mirna_pk+" ("+pk+")");
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
	
	
	
	private int translationToMirna(int mirna_pk) throws SQLException{
		
		
		Statement stmt = null;
		ResultSet rs = null;
		int new_pk = 0;
		
		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna_pk_translation where old_pk ="+ mirna_pk +";";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			while (rs.next()) {
								
				new_pk = rs.getInt("new_pk");
					
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return new_pk ;	
		
	}

	
	private List<MirnaResult> findDatabase(int mirna_pk) throws Exception {
		
		List<MirnaResult> resList = new ArrayList<>();

		Statement stmt = null;
		ResultSet rs = null;
		
		try {

			stmt = con.createStatement();

			String query = "select * from mirna.sequence_hairpin where mirna_pk ="+ mirna_pk +";";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			while (rs.next()) {
								
				MirnaResult mr = new MirnaResult();
				mr.mirna_pk = rs.getInt("mirna_pk");
				mr.sequence = rs.getString("sequence");
				resList.add(mr);
					
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return resList;

	}
	
	
	private void inserta(String sequence, int mirna_pk) throws SQLException {

		String query = "insert into mirna.sequence_hairpin (sequence, mirna_pk) "
				+ "values(?, ?)";

		PreparedStatement stmt = null;
		
		int counter = 0;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, sequence);
			stmt.setInt(2, mirna_pk);
			stmt.execute();
			counter++;
			System.out.println(counter);

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}
	
	
	private class MirnaResult {
		
		int mirna_pk;
		String sequence;
	}

	
	
	public static void main(String[] args) throws Exception {
		
		InsertionOfHairpinSequencesNotFromPlantDatabaseAndMirbase plants = new InsertionOfHairpinSequencesNotFromPlantDatabaseAndMirbase();
		plants.execute();
		
	}


}

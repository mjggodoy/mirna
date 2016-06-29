package mirna.mirbase.sequence;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;


public class InsertionOfHairpinSequencesFromPlantDatabase {

	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	private Connection con = null;

	public InsertionOfHairpinSequencesFromPlantDatabase() throws IOException {
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

			String query = "select * from mirna_raw.plant_mirna_stem_loop;";
			System.out.println("STARTING: " + query);

			rs = stmt.executeQuery(query);

			int limit = -1;

			while (rs.next() && limit!=0) {

				int pk = rs.getInt("pk");
				if (pk%500==0) System.out.println(pk);
				String specie = rs.getString("specie");
				String mirna_id = rs.getString("mirna_id");
				if (mirna_id.startsWith("_")) mirna_id = mirna_id.substring(1);
				String name = specie+"-"+mirna_id;
				String new_name = name.toLowerCase();
				String seq = rs.getString("sequence");
				
				List<MirnaResult> list = findDatabase(new_name);
				
				if ((list != null) && (list.size()!=1)) {
					list = findPreviousIdDatabase(new_name);
				}
				
				if (list==null) {
					// NO HACEMOS NADA (MIRBASE)
				} else if (list.size()==0) {
					//INSERTAR NUEVO HAIRPIN(name);
					insertMirna(name);
				} else if (list.size()==1) {
					//ACTUALIZAR NOMBRE(name)
					if (!name.equals(list.get(0).name)) {
						updateMirna(list.get(0).pk, name, list.get(0).name);
					}
						
				inserta(seq, pk);
					// METER SECUENCIA seq EN HAIRPIN_SEQUENCE RELACIONADA CON PK = list.get(0).pk
					
					
					
				} else {
					throw new Exception("Encontrados "+list.size()+" para "+specie+"-"+mirna_id+" ("+pk+")");
				}
				
				
//				if(findDatabase(new_name) + findDatabase(new_name2) + findDatabase2(new_name3) + findDatabase2(new_name4) + findDatabase2(new_name5) == 1){
//						
//					throw new Exception("Insertar: ");
//				
//				}else{
//					
//					/*StringTokenizer st = new StringTokenizer(new_name, "-");
//
//					
//					while(st.hasMoreTokens()) {
//	
//						String name_specie = st.nextToken();
//						System.out.println(name_specie);
//						String name_stem_loop = st.nextToken();
//						System.out.println(name_stem_loop);
//
//						List<QueryResult> qr = getSequence(name_stem_loop);
//						
//						for (QueryResult result : qr) {
//						
//							
//							String sequence = result.sequence;
//							
//							
//							if(findSequenceMirbase(sequence)){
//								
//								System.out.println("No hacer nada");
//								
//							}else{
//								
//								//System.out.println("Insertar: " + pk + "sequence: " + sequence);	
//								throw new Exception(" sequence: " + sequence);
//								//inserta(sequence, pk);
//							}
//							
//						}
//						   
//					}
//					
//				}
//				
//			*/
//				limit--;
//				//}
//				}
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
			if (con!=null) con.close();
		}
	}
	
	private void updateMirna(int pk, String name, String oldName) throws SQLException {
		
		System.out.println("CAMBIANDO nombre de "+oldName+" a "+name);

		String query = "update mirna.mirna2 set id=? where pk=?";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setInt(2, pk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}
	
	private void insertMirna(String name) throws SQLException {
		
		System.out.println("Insertando "+name);

		String query = "insert into mirna.mirna2 (id, type, old_pks, pre_old_pks) "
				+ "values(?, ?, ?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, "hairpin");
			stmt.setString(3, "");
			stmt.setString(4, "");
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}

	
	private List<MirnaResult> findDatabase(String mirna) throws Exception {
		
		List<MirnaResult> resList = new ArrayList<>();

		Statement stmt = null;
		ResultSet rs = null;
		
		boolean fromMirbase = false;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 where LOWER(id) ='"+ mirna+"'";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			while (rs.next()) {
								
				String type = rs.getString("type");
				int mirbasePk = rs.getInt("mirbase_pk");
				if (mirbasePk!=0) fromMirbase = true;
				if ("dead".equals(type)) fromMirbase = true;
				
				if (fromMirbase) {
					//System.out.println(mirna + " - " + rs.getString("id"));
				}

				//return true;
				if ("hairpin".equals(type)) {
					MirnaResult mr = new MirnaResult();
					mr.pk = rs.getInt("pk");
					mr.name = rs.getString("id");
					resList.add(mr);
				}
					
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		if (fromMirbase) resList = null;
		return resList;

	}
	
	private List<MirnaResult> findPreviousIdDatabase(String mirna) throws Exception {
		
		List<MirnaResult> resList = new ArrayList<>();

		Statement stmt = null;
		ResultSet rs = null;
		
		boolean fromMirbase = false;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 where LOWER(previous_id) ='"+ mirna+"'";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			while (rs.next()) {
								
				String type = rs.getString("type");
				int mirbasePk = rs.getInt("mirbase_pk");
				if (mirbasePk!=0) fromMirbase = true;
				if ("dead".equals(type)) fromMirbase = true;
				
				if (fromMirbase) {
					//System.out.println(mirna + " - " + rs.getString("id"));
				}

				//return true;
				if ("hairpin".equals(type)) {
					MirnaResult mr = new MirnaResult();
					mr.pk = rs.getInt("pk");
					mr.name = rs.getString("id");
					resList.add(mr);
				}
					
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		if (fromMirbase) resList = null;
		return resList;

	}

	private int findDatabase2(String mirna) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;

		String res = null;
		int counter= 0;

		try {

			stmt = con.createStatement();

			String query = "select * from mirna.mirna2 where previous_id='"+ mirna +"' and mirbase_pk is null;";


			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);


			while (rs.next()) {

				//return true;
				counter++;
				
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		//return (counter==1);
		return counter;

	}
	
	
	private void inserta(String sequence, int mirna_pk) throws SQLException {

		String query = "insert into mirna.sequence_hairpin (sequence, mirna_pk) "
				+ "values(?, ?)";

		PreparedStatement stmt = null;

		try {

			stmt = con.prepareStatement(query);
			stmt.setString(1, sequence);
			stmt.setInt(2, mirna_pk);
			stmt.execute();

		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}

	}
	
	private List<QueryResult> getSequence(String new_name) throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		List<QueryResult> res = new ArrayList<>();
		
		try {

			stmt = con.createStatement();
			String query = "select * from mirna_raw.plant_mirna_stem_loop where mirna_id='" + new_name + "'";			
			rs = stmt.executeQuery(query);
			
			int limit = -1;
			
			while (rs.next() && limit!=0) {
				
				QueryResult q = new QueryResult();
				q.mirna_id = rs.getString("mirna_id");
				q.sequence = rs.getString("sequence");
				res.add(q);
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (stmt!=null) stmt.close();
		}
		
		return res;
		
	}
	
	
	private boolean findSequenceMirbase(String sequence_plant) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;


		try {

			stmt = con.createStatement();

			String query = "select * from mirna.sequence_hairpin where sequence ='" + sequence_plant+"'";

			// execute the query, and get a java resultset
			rs = stmt.executeQuery(query);

			//int counter = 0;

			while (rs.next()) {

				return true;
				
			}

			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs!=null) rs.close();
			if (stmt!=null) stmt.close();
		}

		return false;

	}
	
	
	private class MirnaResult {
		int pk;
		String name;
	}

	private class QueryResult {
		
		int pk;
		String mirna_id;
		String sequence;
	
	}
	

	public static void main(String[] args) throws Exception {
		
		InsertionOfHairpinSequencesFromPlantDatabase plants = new InsertionOfHairpinSequencesFromPlantDatabase();
		plants.execute();
		
		
	}


}

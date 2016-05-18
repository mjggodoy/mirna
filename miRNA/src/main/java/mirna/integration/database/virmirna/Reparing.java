package mirna.integration.database.virmirna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import mirna.integration.database.NewMirnaDatabase;
import mirna.integration.exception.MiRnaException;

import org.hibernate.Session;


public class Reparing extends NewMirnaDatabase {
	
	
	public Reparing(String tableName) throws MiRnaException {
		super(tableName);
	}


	public void insertInTable() throws Exception {

		Connection con = null;
	

		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			Statement stmt2 = (Statement) con.createStatement(); 
			Statement stmt3 = (Statement) con.createStatement(); 



			System.out.println("Ejecutar consulta");
			String query = "SELECT * from mirna_raw.virmirna2";
			ResultSet rs = stmt.executeQuery(query);
			int pk_interaction_data_ini = 67487318;
		
				while(rs.next() && pk_interaction_data_ini<=67487859){
					
					
					String target_process = rs.getString("target_process");
					String query2 = "SELECT pk from mirna.biological_process where name="+ "'"+target_process+"'";
					ResultSet rs2 = stmt2.executeQuery(query2);
					if(rs2.next()){
						int pk_biological_process = rs2.getInt("pk");
						String sql = "INSERT INTO mirna.interaction_data_has_biological_process (interaction_data_pk, biological_process_pk)" +
									 " VALUES ("+pk_interaction_data_ini+","+pk_biological_process+");";
					stmt3.executeUpdate(sql); 					 
					pk_interaction_data_ini++;	
					System.out.println(pk_interaction_data_ini);
					}
					
				}
				

				
			
			System.out.println("Finishing!");
			stmt.close();
			stmt2.close();
			rs.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}

	}		

	
	
	public static void main(String[] args) throws Exception {
	
		Reparing rp = new Reparing(null); 
		//rp.insertInTable();
		
	}



	@Override
	protected void processRow(Session session, ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void insertInTable(String inputFile) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.ExpressionData;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Target;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de miRDB
 * 
 * @author Esteban López Camacho
 *
 */
public class miRDB extends MirnaDatabase {
	
	private final String tableName = "miRDB";
	
	public miRDB() throws MiRnaException { super(); }
	
	public void insertInTable(String csvInputFile) throws Exception {
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				
					
					String accesionNumber = tokens[0];
					String target = tokens[1];
					String score = tokens[2];
					

					

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ accesionNumber + "','"
							+ target + "','"
							+ score + "')";
					
					stmt.executeUpdate(query);
		
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	@Override
	public void insertIntoSQLModel() throws Exception {

		Connection con = null;
		
		try {
			con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			Statement stmt = (Statement) con.createStatement();
			
			// our SQL SELECT query. 
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM " + tableName;
			System.out.println("STARTING: " + query);
			
			// execute the query, and get a java resultset
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the java resultset
			//int count = 0;


			rs.next();
			// CAMBIAR ESTO:
			
			String miRNA = rs.getString("mir");
			String target_name = rs.getString("target");
			String score = rs.getString("score");
			
			MiRna miRna = new MiRna();
			miRna.setName(miRNA);
			
			InteractionData id = new InteractionData();
			id.setScore(score);
			
			
			Target target = new Target();
			target.setName(target_name);
			
			System.out.println(miRna);
			System.out.println(id);
			System.out.println(target);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		miRDB mirdb = new miRDB();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/MirTarget2_v4.0_prediction_result.txt";
		//mirdb.insertInTable(inputFile);
		
		mirdb.insertIntoSQLModel();
		
	}

}
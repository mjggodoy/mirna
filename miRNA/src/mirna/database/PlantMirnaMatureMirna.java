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
import mirna.beans.Hairpin;
import mirna.beans.Mature;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

public class PlantMirnaMatureMirna extends MirnaDatabase {

	private final String tableName = "plant_mirna_mature_mirna";
	
	public PlantMirnaMatureMirna() throws MiRnaException { super(); }

	@Override
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
			
			String specie = "", mirnaid = "";
			
			while (((line = br.readLine()) != null)) {
				
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\n");
				
				if(line.startsWith (">")){
	
					String specie1 = tokens[0];
					int index1 = specie1.indexOf(">");
					int index2 = specie1.indexOf("-");
					specie = specie1.substring(index1+1, index2);
					mirnaid = specie1.substring(index2+1);
					
//					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
//							+ specie2 + "','"
//							+ mirnaid + "')";
//					
//					stmt.executeUpdate(query);
	
				}else{
					
					String sequence = tokens[0];
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ specie + "','"
							+ mirnaid + "','"
							+ sequence + "')";
					
					stmt.executeUpdate(query);
					
				}
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": (" + tokens[j].length() + ") " + tokens[j]);
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
			
			String specie = rs.getString("specie").toLowerCase().trim();
			String mirna_id = rs.getString("mirna_id").toLowerCase().trim();
			String sequence = rs.getString("sequence").toLowerCase().trim();
		
			MiRna miRna = new MiRna();
			miRna.setName(mirna_id);
			
			Organism organism = new Organism();
			organism.setSpecie(specie);
			
			Mature mature = new Mature();
			mature.setSequence(sequence);
			
			System.out.println(miRna);
			System.out.println(organism);
			System.out.println(mature);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	public static void main(String[] args) throws Exception{
		
		PlantMirnaMatureMirna plant = new PlantMirnaMatureMirna();
		
		String inputFile = "/Users/esteban/Softw/miRNA/plant_mirna/all_mature.txt";
		plant.insertInTable(inputFile);
	
	}
	
}

package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mirna.beans.Disease;
import mirna.beans.EnvironmentalFactor;
import mirna.beans.ExpressionData;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Organism;
import mirna.beans.Target;
import mirna.beans.Transcript;
import mirna.exception.MiRnaException;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de miREnvironment
 * 
 * @author Esteban López Camacho
 *
 */
public class miREnvironment extends MirnaDatabase {
	
	private final String tableName = "miREnvironment";
	
	public miREnvironment() throws MiRnaException {
		super();
	}
	
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
	
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
					String mir = tokens[0];
					String name = tokens[1];
					String name2 = tokens[2];
					String name3 = tokens[3];
					String disease = tokens[4].replaceAll("'", "\\\\'");
					String enviromenentalFactor = tokens[5].replaceAll("'", "\\\\'");
					String treatment = tokens[6].replaceAll("'", "\\\\'");
					String cellularLine = tokens[7].replaceAll("'", "\\\\'");
					String specie = tokens[8];
					String description = tokens[9].replaceAll("'", "\\\\'");
					String pubmedId = tokens[10];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ mir + "','"
							+ name + "','"
							+ name2 + "','"
							+ name3 + "','"
							+ disease + "','"
							+ enviromenentalFactor + "','"
							+ treatment + "','"
							+ cellularLine + "','"
							+ specie + "','"
							+ description + "','"
							+ pubmedId + "')";
					
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
	public void insertIntoSQLModel()
			throws Exception {

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
			
			String id = rs.getString("mir");
			String name = rs.getString("name");
			String name2 = rs.getString("name2");
			String name3 = rs.getString("name3").toLowerCase().trim();
			String disease_name = rs.getString("disease").toLowerCase().trim();
			String environmentalFactor = rs.getString("enviromentalFactor").toLowerCase().trim();
			String treatment = rs.getString("treatment").toLowerCase().trim();
			String cellularLine = rs.getString("cellularLine").toLowerCase().trim();
			String specie_name = rs.getString("specie");
			String description = rs.getString("description");
			String pubmedId = rs.getString("pubmedId");

			
			MiRna miRna = new MiRna();
			miRna.setName(name);
			miRna.setName(name2);
			miRna.setName(name3);

			Disease disease = new Disease();
			disease.setName(disease_name);
			
			EnvironmentalFactor ef = new EnvironmentalFactor();
			ef.setName(environmentalFactor);
			
			ExpressionData ed = new ExpressionData();
			ed.setTreatment(treatment);
			ed.setCellularLine(cellularLine);
			ed.setDescription(description);
			ed.setPubmedId(pubmedId);
			ed.setProvenanceId(id);
			ed.setProvenance("miREnvironment");
			
			Organism specie = new Organism();
			specie.setName(specie_name);
			
			
			System.out.println(miRna);
			System.out.println(disease);
			System.out.println(ef);
			System.out.println(ed);
			System.out.println(specie);
			
			// FIN DE CAMBIAR ESTO
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		miREnvironment mirEnvironment = new miREnvironment();
		
		//String inputFile = "/Users/esteban/Softw/miRNA/mirendata.txt";
		//mirEnvironment.insertInTable(inputFile);
		
		mirEnvironment.insertIntoSQLModel();
		
	}

}
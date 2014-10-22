package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de mirDIP
 * 
 * @author Esteban López Camacho
 *
 */
public class mirDIP implements IMirnaDatabaseLegacy {
	
	private String csvInputFile;
	
	public mirDIP(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}
	
	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	public void insertInTable(String tableName, Integer maxLines) throws Exception {
		
		// URL of Oracle database server
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					
					String accesionnumber = tokens[0];
					String gene = tokens[1];
					String source = tokens[2];
					String rank = tokens[3];
					

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ accesionnumber + "','"
							+ gene + "','"
							+ source + "','"
							+ rank + "')";
					
					stmt.executeUpdate(query);
	
				}
	
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
	public void insertIntoSQLModel(String originTable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertIntoSQLModel(String originTable, Integer maxLines)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/mirDIP/mirDIP-All-Data-Version1.0.txt";
		mirDIP phenomir = new mirDIP(inputFile);
		phenomir.insertInTable("mirDIP");
		
		/*
		String inputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.txt";
		//String outputFile = "/Users/esteban/Softw/miRNA/miRCancerMarch2014.rdf";
		//Integer maxLines = 5;
		
		MiRCancer miRCancer = new MiRCancer(inputFile);
		//miRCancer.buildRdf(outputFile, maxLines);
		miRCancer.insertInTable("MiRnaCancer");
		miRCancer.insertIntoSQLModel("MiRnaCancer");
		*/
	}

}
package mirna.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de RepTar
 * 
 * @author Esteban López Camacho
 *
 */
public class mirDip1 implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public mirDip1(String csvInputFile) {
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
					String geneId = tokens[1];
					String score = tokens[2];
					

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ accesionnumber + "','"
							+ geneId + "','"
							+ score + "','" +  "')";
					
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
		
		String inputFile = "/Users/esteban/Softw/miRNA/MirTarget2_v4.0_prediction_result.txt";
		mirDip1 mirDip1 = new mirDip1(inputFile);
		mirDip1.insertInTable("miRDip1");
		
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
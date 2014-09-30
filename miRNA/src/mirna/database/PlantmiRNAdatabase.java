package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class PlantmiRNAdatabase {

	private String csvInputFile1;
	private String csvInputFile2;

	
	public PlantmiRNAdatabase(String csvInputFile1, String cvsInputFile2) {
		this.csvInputFile1 = csvInputFile1;
		this.csvInputFile2 = csvInputFile2;

	}
	
	
	public void insertInTableStemLoop(String tableName) throws Exception {
		
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile1);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
				
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\n");
				
				if(line.contains(">")){
	
					String specie1 = tokens[0];
					int index1 = specie1.indexOf(">");
					int index2 = specie1.indexOf("-");
					String specie2 = specie1.substring(index1, index2);
					String mirnaid = specie1.substring(index2);
					
					
					
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ specie2 + "','"
							+ mirnaid + "')";
					
					stmt.executeUpdate(query);
	
				}else{
					
					
					String sequence = tokens[1];
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
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
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}		
	}
	
	
	public void insertInTableMature(String tableName) throws Exception {
		
		
		String url = "jdbc:mysql://localhost:3306/mirna_raw";
		
		String user = "mirna";
		String password = "mirna";
		
		Connection con = null;
		String line = null;
		String[] tokens = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile2);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			while (((line = br.readLine()) != null)) {
				
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\n");
				
				if(line.contains(">")){
	
					String specie1 = tokens[0];
					int index1 = specie1.indexOf(">");
					int index2 = specie1.indexOf("-");
					String specie2 = specie1.substring(index1, index2);
					String mirnaid = specie1.substring(index2);
					
					
					
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ specie2 + "','"
							+ mirnaid + "')";
					
					stmt.executeUpdate(query);
	
				}else{
					
					
					String sequence = tokens[1];
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
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
					System.out.println(j + ": " + tokens[j]);
				}
			}
			e.printStackTrace();
		} finally {
			if (con!=null) con.close();
		}		
	}
		
		
		
	public void insertInTableStemLoop(String tableName, Integer maxLines) throws Exception {
		this.insertInTableStemLoop(tableName, null);
	}
	
	public void insertInTableMature(String tableName, Integer maxLines) throws Exception {
		this.insertInTableMature(tableName, null);
	}
	
	
	
	public static void main(String[] args) throws Exception{
		
		String inputFile1 = "/Users/esteban/Softw/miRNA/all_stem_loop.txt";
		String inputFile2 = "/Users/esteban/Softw/miRNA/all_mature.txt";
		PlantmiRNAdatabase plantmiRNAdatabase = new PlantmiRNAdatabase(inputFile1, inputFile2);
		plantmiRNAdatabase.insertInTableStemLoop("stem_loop_mirna");
		plantmiRNAdatabase.insertInTableStemLoop("mature_mirna");
	
	}
	
	
	
	
}

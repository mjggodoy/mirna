package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de miREnvironment
 * 
 * @author Esteban López Camacho
 *
 */
public class miREnvironment implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public miREnvironment(String csvInputFile) {
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
	public void insertIntoSQLModel(String originTable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertIntoSQLModel(String originTable, Integer maxLines)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/mirendata.txt";
		miREnvironment mirEnvironment = new miREnvironment(inputFile);
		mirEnvironment.insertInTable("miREnvironment");
		
	}

}
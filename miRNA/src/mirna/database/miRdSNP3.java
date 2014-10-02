package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP3 extends miRdSNP {
	
	public miRdSNP3(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}
	
	public void insertInTable(String tableName, Integer maxLines) throws Exception {
		

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
				
				//tokens = StringUtils.splitPreserveAllTokens(line, ",");
				tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				
				for (int i=0; i<tokens.length; i++) {
					tokens[i] = quitarComillas(tokens[i]);
				}
	
				String geneName = tokens[0];
				String refseqId = tokens[1];
				String miR = tokens[2];
				String SNP = tokens[3];
				String diseases = tokens[4].replaceAll("'", "\\\\'");
				String distance = tokens[5];
				
				String expConf = "";//tokens[6];
				
				if (tokens.length==7) {
					expConf = tokens[6];
					
					if (!"Yes".equals(expConf)) {
						br.close();
						throw new Exception(tokens.length + " tokens found!");
					}
				}
					
				if (tokens.length>7) {
					br.close();
					throw new Exception(tokens.length + " tokens found!");
				}
			
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ geneName + "','"
						+ refseqId + "','"
						+ miR + "','"
						+ SNP + "','"
						+ diseases + "','"
						+ distance + "','"
						+ expConf + "')";
				
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
		
	public void insertIntoSQLModel(String originTable) throws Exception {
		this.insertIntoSQLModel(originTable, null);
	}

	public void insertIntoSQLModel(String originTable, Integer maxLines) throws Exception {
	
	}
	
}
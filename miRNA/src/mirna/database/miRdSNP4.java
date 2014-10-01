package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class miRdSNP4 extends miRdSNP {
	
	public miRdSNP4(String csvInputFile) {
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
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				
					String gene_name = tokens[0];
					String refseq_name = tokens[1];
					String miRNA = tokens[2];
					String snp = tokens[3];
					String disease = tokens[4];
					String distance = tokens[5];
					String expertimental_confirmation = tokens[6];
				
					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ gene_name + "','"
							+ refseq_name + "','"
							+ miRNA + "','"
							+ snp + "','"
							+ disease + "')";
					
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
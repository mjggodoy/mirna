package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class VirmiRNA3 extends VirmiRNA {
	
	private String csvInputFile;
	
	public VirmiRNA3(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}
	
	public void insertInTable(String tableName, Integer numlines) throws Exception {
		
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
				String VMT_id = tokens[0];
				String virus = tokens[1];
				String virus_full_name = tokens[2];
				String taxonomy = tokens[3];
				String miRNA = tokens[4];
				String gene = tokens[5];
				String Uniprot = tokens[6];
				String organism  = tokens[7];
				String cell_line = tokens[8];
				String method = tokens[9];
				String sequence_target = tokens[10];
				String start_target = tokens[11];
				String end_target = tokens[12];
				String region_target = tokens[13];
				String target_reference = tokens[14];
				String pubmed_id = tokens[15];				

				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ VMT_id + "','"
						+ virus + "','"
						+ virus_full_name + "','"
						+ taxonomy + "','"
						+ miRNA + "','"
						+ gene + "','"
						+ Uniprot + "','"
						+ organism + "','"
						+ cell_line + "','"
						+ method + "','"
						+ sequence_target + "','"
						+ start_target + "','"
						+ end_target + "','"
						+ region_target  + "','"
						+ target_reference  + "','"
						+ pubmed_id + "')";
				
				
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

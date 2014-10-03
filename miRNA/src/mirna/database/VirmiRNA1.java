package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class VirmiRNA1 extends VirmiRNA{
	

	
	public VirmiRNA1(String csvInputFile) {
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
				String id_virus = tokens[0];
				String virus_full_name = tokens[1];
				String link_virus = tokens[2];
				String miRNA = tokens[3];
				String miRNA_sequence = tokens[4];
				String length = tokens[5];
				String GC_proportion = tokens[6];
				String arm = tokens[7];
				String pre_miRNA = tokens[8];
				String pre_miRNA_sequence = tokens[9];
				String cell_line = tokens[10];
				String method = tokens[11];
				String pubmed_id = tokens[12];

				
				String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ id_virus + "','"
						+ virus_full_name + "','"
						+ link_virus + "','"
						+ miRNA + "','"
						+ miRNA_sequence + "','"
						+ length + "','"
						+ GC_proportion + "','"
						+ arm + "','"
						+ pre_miRNA + "','"
						+ pre_miRNA_sequence + "','"
						+ cell_line  + "','"
						+ method  + "','"
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

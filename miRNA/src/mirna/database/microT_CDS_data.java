package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

public class microT_CDS_data implements IMirnaDatabase {
	
	
private String csvInputFile;
	
	public microT_CDS_data(String csvInputFile) {
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
		String[] tokens2 = null;

		
		try {
			
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			br.readLine();
			
			String transcript_id = null;
			String gene_id = null;
			String miRNA = null;
			String miTG_score = null;
			String region = null;
			String location = null;
			String chromosome = null;
			String coordinates = null;
			
			while (((line = br.readLine()) != null) && ((maxLines==null) || (count<maxLines))) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, ",");
	
				if (line != null && !line.startsWith("UTR3") && !line.startsWith("CDS")) {
					
					
					transcript_id = tokens[0];
					gene_id = tokens[1];
					miRNA = tokens[2];
					miTG_score = tokens[3];
					
			
	
				}else{
					
					region = tokens[0];
					location = tokens[1];
					
					tokens2 = StringUtils.splitPreserveAllTokens(tokens[1], ":");
					chromosome = tokens2[0];
					coordinates = tokens2[1];
					
					
					String  query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ transcript_id + "','"
							+ gene_id + "','"
							+ miRNA + "','"
							+ miTG_score + "','"
							+ region + "','"
							+ chromosome + "','"
							+ coordinates + "')";
					
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
		
		String inputFile = "/Users/esteban/Softw/miRNA/microT_CDS.csv";
		microT_CDS_data microT_CDS_data = new microT_CDS_data(inputFile);
		microT_CDS_data.insertInTable("microT_CDS_data");
		
	}
	

}

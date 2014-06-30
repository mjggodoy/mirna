package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

import beans.Disease;
import beans.MiRna;

public class TarBase {
	
	
	private String csvInputFile;

	public TarBase(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}

	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}

	
	
	public void insertInTable(String tableName, Integer maxLines)
			throws Exception {
		String url = "jdbc:mysql://localhost:3306/mirna";
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

			while (((line = br.readLine()) != null)
					&& ((maxLines == null) || (count < maxLines))) {
		
				count++;

				System.out.println(count);

				tokens = StringUtils.splitPreserveAllTokens(line, "\t");

				if (line != null) {
					
					MiRna miRna = new MiRna();
					miRna.setName(tokens[2]);

					
					Disease disease = new Disease();
					disease.setName(tokens[2]);

					
				
				}
		
			}
		
		
			}catch (Exception e) {

			e.printStackTrace();
			System.out.println(line);
			for (int j = 0; j < tokens.length; j++) {
				System.out.println(j + ": " + tokens[j]);
			}
			e.printStackTrace();

		}
		
		

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}

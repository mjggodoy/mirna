package mirna.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;

/**
 * Código para procesar los datos de Mir2Disease
 * 
 * @author Esteban López Camacho
 *
 */
public class Mir2Disease implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public Mir2Disease(String csvInputFile) {
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
		
		String query = "";
		
		try {
			con = DriverManager.getConnection(url, user, password);
			Statement stmt = (Statement) con.createStatement(); 
			
			FileReader fr = new FileReader(csvInputFile);
			BufferedReader br = new BufferedReader(fr);
	
			int count = 0;
	
			while (((line = br.readLine()) != null) && ((maxLines==null) || (count<maxLines))) {
	
				count++;
				System.out.println(count + " : " + line);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				String mirna = tokens[0];
				String disease = tokens[1].replaceAll("'", "\\\\'");
				String expression = tokens[2];
				String method = tokens[3];
				String date = tokens[4];
				String reference = tokens[5].replaceAll("'", "\\\\'");

				query = "INSERT INTO " + tableName + " VALUES (NULL, '"
						+ mirna + "','"
						+ disease + "','"
						+ expression + "','"
						+ method + "','"
						+ date + "','"
						+ reference + "')";
				
				stmt.executeUpdate(query);
						
	
			}
			fr.close();
			br.close();
			stmt.close();
		} catch (Exception e) {
			if (line!=null) {
				System.out.println(line);
				for (int j = 0; j < tokens.length; j++) {
					System.out.println(j + ": " + tokens[j]);
				}
			}
			System.out.println("QUERY =");
			System.out.println(query);
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
	
	public void specificFileFix() throws IOException {
		
		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;
		
		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {
			
			if (!line1.startsWith("hsa")) {
				line0 = line0 + " " + line1;
				line1 = br.readLine();
			}
			
			pw.println(line0);
			line0 = line1;
			
		}
		
		if (line0!=null) pw.println(line0);
		
		br.close();
		pw.close();
		
		this.csvInputFile = newFile;
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/mir2disease_AllEntries.txt";
		Mir2Disease mir2disease = new Mir2Disease(inputFile);
		mir2disease.specificFileFix();
		mir2disease.insertInTable("mir2disease");
		
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
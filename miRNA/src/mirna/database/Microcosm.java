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
 * Código para procesar los datos de Phenomir
 * 
 * @author Esteban López Camacho
 *
 */
public class Microcosm implements IMirnaDatabase {
	
	private String csvInputFile;
	
	public Microcosm(String csvInputFile) {
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
			
			while (((line = br.readLine()) != null) && ((maxLines==null) || (count<maxLines))) {
	
				count++;
				System.out.println(count);
				
				tokens = StringUtils.splitPreserveAllTokens(line, "\t");
	
				if (line != null) {
					
					if (tokens.length<13) {
						count++;
						System.out.println(count);
						String line2 = br.readLine();
						if (line2.startsWith(",,")) line2 = line2.substring(2);
						line = line + "\t" + line2;
						tokens = StringUtils.splitPreserveAllTokens(line, "\t");
					}
					
					String phenomicid = tokens[0];
					String pmid = tokens[1];
					String disease = tokens[2];
					String diseasesubId = tokens[3];
					String class_ = tokens[4];
					String miRNA = tokens[5];
					String accession = tokens[6];
					String expression = tokens[7];
					String foldchangemin = tokens[8];
					String foldchangemax = tokens[9];
					String id = tokens[10];
					String name = tokens[11];
					String method = tokens[12];

					String query = "INSERT INTO " + tableName + " VALUES (NULL, '"
							+ phenomicid + "','"
							+ pmid + "','"
							+ disease + "','"
							+ diseasesubId + "','"
							+ class_ + "','"
							+ miRNA + "','"
							+ accession + "','"
							+ expression + "','"
							+ foldchangemin + "','"
							+ foldchangemax + "','"
							+ id + "','"
							+ name + "','"
							+ method + "')";
					
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
	
	public void specificFileFix() throws IOException {
		
		String newFile = csvInputFile + ".new";
		PrintWriter pw = new PrintWriter(newFile);
		
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		String line0;
		String line1;
		
		line0 = br.readLine();

		while ( (line0!=null) && ((line1 = br.readLine()) != null) ) {
			
			if (line1.startsWith(",,")) {
				line0 = line0.trim() + "\t\t" + line1.substring(2);
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
		
		String inputFile = "/Users/esteban/Softw/miRNA/phenomir-2.0.tbl";
		Microcosm phenomir = new Microcosm(inputFile);
		phenomir.specificFileFix();
		phenomir.insertInTable("phenomir");
		
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
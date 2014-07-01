package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.commons.io.output.WriterOutputStream;
import org.apache.commons.lang.StringUtils;

import beans.DataExpression;
import beans.Disease;
import beans.Gene;
import beans.InteractionData;
import beans.MiRna;
import beans.Transcript;

public class MicroTV4_data {
	
	
	private String csvInputFile;

	public MicroTV4_data(String csvInputFile) {
		this.csvInputFile = csvInputFile;
	}

	public void insertInTable(String tableName) throws Exception {
		this.insertInTable(tableName, null);
	}
	
	
	private static String fixLine(String line) {
		
		
		
		return null;
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

				tokens = StringUtils.splitPreserveAllTokens(line, ",");

				if (line != null) {
					
					MiRna miRna = new MiRna();
					miRna.setName(tokens[1]);
					
					Transcript transcript = new Transcript();
					transcript.setTranscriptID(tokens[0]);
					transcript.setLocation(tokens[5]);

					Gene gene = new Gene();
					gene.setGeneId(tokens[2]);
					
					InteractionData interactionData = new InteractionData();
					interactionData.setMiTG_score(tokens[4]);

					
					
					String query = "INSERT INTO " + tableName
							+ " VALUES (NULL, '" + tokens[0] + "','"
							+ tokens[1] + "','" + tokens[2] + "','" + tokens[3]
							+ "','" + tokens[4] + "',')";

					stmt.executeUpdate(query);

					
			
				}
			}
			
			
		
		}catch (Exception e) {

			e.printStackTrace();
			System.out.println(line);
			for (int j = 0; j < tokens.length; j++) {
				System.out.println(j + ": " + tokens[j]);
			}
			e.printStackTrace();

		}}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		String inputFile = "/Users/esteban/Softw/miRNA/microtv4_data.txt";
		String outputFile = "/Users/esteban/Softw/miRNA/AllEntries.rdf";
		String outputFile1 = "/Users/esteban/Softw/miRNA/microtv4_data_fixed.txt";
		String tableName = "MicroTV4_data";
		int maxLines = 11000;
		
		
		FileReader fr = new FileReader(inputFile);
		BufferedReader br = new BufferedReader(fr);
		PrintWriter pw = new PrintWriter(outputFile1);
		
		
		String line;
		
		int count=0;
		String line1=null;
		
		
		br.readLine();
		
		while ((line = br.readLine()) != null) {
			
			count ++;
			
			if(count/2==0){
				
				 line1 = line;
				
				
				
			}
			
			pw.write(line + "," + line1 + "\n");
			
			
			
			
			
		}
		
		br.close();
		pw.close();
		
		
		
		MicroTV4_data microTV4_data = new MicroTV4_data(outputFile1);

		
		
	}
	
	
	


	
	

}

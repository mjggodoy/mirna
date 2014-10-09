package mirna.rdfconverters;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import mirna.beans.Gene;
import mirna.beans.InteractionData;
import mirna.beans.MiRna;
import mirna.beans.Transcript;

import org.apache.commons.lang.StringUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

public class microT_CDS_data {
	
	
	private String csvInputFile;
	
	public microT_CDS_data(String csvInputFile) {
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
			
			
			fr.close();
			br.close();
			stmt.close();
			

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(line);
			for (int j = 0; j < tokens.length; j++) {
				System.out.println(j + ": " + tokens[j]);
			}
			e.printStackTrace();

		}
	}
	
	public void buildRdf(String rdfOutputFile, Integer maxLines)
			throws Exception {
	
	
		FileReader fr = new FileReader(csvInputFile);
		BufferedReader br = new BufferedReader(fr);
		OutputStream out = new FileOutputStream(rdfOutputFile);

		String namespace = "http://khaos.uma.es/RDF/miRna.owl#";
		
		
		Model model = ModelFactory.createDefaultModel();

		int interactiondataCount = 0;
		String line;
		while ((line = br.readLine()) != null) {
			
			
			System.out.println("linea =");
			System.out.println(line);
			interactiondataCount++;
			System.out.println(interactiondataCount);
			String[] tokens = StringUtils.splitPreserveAllTokens(line, "\t");

			if (line!=null){
				
				try{
					
					MiRna miRna = new MiRna();
					miRna.setName(tokens[1]);

					Transcript transcript = new Transcript();
					transcript.setTranscriptID(tokens[0]);
					transcript.setLocation(tokens[5]);
					
					InteractionData interactiondata = new InteractionData();
					interactiondata.setMiTG_score(tokens[4]);


					Gene gene = new Gene();
					gene.setGeneId(tokens[2]);


					Resource miRNA = model
							.createResource(
									namespace + "miRNA/" + miRna.getName())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "miRNA"));

					Resource transcript2 = model.createResource(
							namespace + "Transcript/" + transcript.getTranscriptID())
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "ID"), transcript.getLocation())
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Transcript"));

					model.createResource(
							namespace + "InteractionData_"
									+ interactiondataCount)
							
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "MiTG_score"),
											interactiondata.getMiTG_score())
											
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"), miRNA)
											
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "involvesInteraction"), transcript2)				
											
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "InteractionData"));
				
					model.createResource(
							namespace + "Gene"
									+ gene.getName())
							
							.addProperty(
									ResourceFactory.createProperty(namespace
											+ "producesTranscript"), transcript2)
											
							.addProperty(
									RDF.type,
									ResourceFactory.createResource(namespace
											+ "Gene"));				
					
					
					
				
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println(line);
					for (int j = 0; j < tokens.length; j++) {
						System.out.println(j + ": " + tokens[j]);
					}
					throw e;
				}
				
				model.write(out);

				
				
				}
			

			out.close();
			fr.close();
			br.close();
			
			
			

			
		}
	
	}
	
	public static void main(String[] args) throws Exception {
		
		String inputFile = "/Users/esteban/Softw/miRNA/microT_CDS_data.csv";
		String outputFile = "/Users/esteban/Softw/miRNA/microT_CDS_data.rdf";
		String outputFile1 = "/Users/esteban/Softw/miRNA/microT_CDS_data_fixed.txt";
		String tableName = "MicroT_CS_data";
		int maxLines = 11000;

		FileReader fr = new FileReader(inputFile);
		BufferedReader br = new BufferedReader(fr);
		PrintWriter pw = new PrintWriter(outputFile1);

		String line;

		int count = 0;
		String line1 = null;

		br.readLine();

		while ((line = br.readLine()) != null) {

			count++;
			
			System.out.println(count);

			if (line.contains(":")) {

				line1 = line;

			}

			pw.write(line + "," + line1 + "\n");

		}

		br.close();
		pw.close();

		MicroTV4_data microTV4_data = new MicroTV4_data(outputFile1);
		microTV4_data.insertInTable(tableName, maxLines);
		microTV4_data.buildRdf(outputFile, maxLines);
		
		
		
		
	}

}
